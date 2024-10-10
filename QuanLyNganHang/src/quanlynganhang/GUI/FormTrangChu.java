package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import quanlynganhang.BUS.CalendarBUS;
import quanlynganhang.BUS.WeatherBUS;
import quanlynganhang.DTO.CalendarDTO;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.DTO.WeatherDTO;
import quanlynganhang.GUI.model.slider.Slide1;
import quanlynganhang.GUI.model.slider.Slide2;
import quanlynganhang.GUI.model.slider.Slide3;
import quanlynganhang.GUI.model.slider.Slide4;
import quanlynganhang.GUI.model.slider.Slide5;

public class FormTrangChu extends javax.swing.JPanel {

    private CardLayout weatherStatCLayout;
    private boolean isCTemp;
    private WeatherDTO weatherData, weatherInfo;
    private String cityName;

    public FormTrangChu(TaiKhoanNVDTO taiKhoanNV, ChucVuDTO chucVu) {
        cityName = "";
        isCTemp = true;
        
        initComponents();
        jPBody.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPTinTuc.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPThu.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPGhiChu2.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPNgay.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPThangVaNam.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPSuKien.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPanel3.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPanel11.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPanel12.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPanel13.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPanel18.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPInitWeather.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPLoadInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");

        btnSearchCity.setEnabled(false);

        txtWeatherSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập thành phố muốn xem thời tiết...");
        slideshow1.initSlideshow(new Slide1(), new Slide2(), new Slide3(), new Slide4(), new Slide5());

        weatherStatCLayout = (CardLayout) (jPWeatherInfo.getLayout());
        weatherStatCLayout.show(jPWeatherInfo, "jPInitWeather");

        initCalendar();
    }

    private void initCalendar() {
        CalendarDTO calendar = CalendarBUS.getInfoToday();

        if (calendar != null) {
            lbWeekday.setText("Hôm nay - " + calendar.getWeekday());
            lbDay.setText(calendar.getCalDay());
            lbMonthAndYear.setText(calendar.getCalMonth() + ", " + calendar.getCalYear());
            lbEvent.setText("<html>" + calendar.getEvent() + "</html>");
        }
    }

    private void initWeather(String cityNameInput) {
        enableForm(false);
        weatherStatCLayout.show(jPWeatherInfo, "jPLoadInfo");

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                weatherData = WeatherBUS.getCityLocation(cityNameInput);

                return weatherData == null ? false : true;
            }

            @Override
            protected void done() {
                Boolean success;
                try {
                    success = get();
                } catch (Exception ex) {
                    success = null;
                    ex.printStackTrace();
                }
                if (success) {
                    weatherInfo = weatherData;
                    fillWeatherInfo(isCTemp);

                    weatherStatCLayout.show(jPWeatherInfo, "jPShowInfo");
                } else if (lbCityName.getText().equals("(Chưa có)")) {
                    weatherStatCLayout.show(jPWeatherInfo, "jPInitWeather");
                } else {
                    weatherStatCLayout.show(jPWeatherInfo, "jPShowInfo");
                }

                enableForm(true);
            }
        };
        worker.execute();
    }

    private void fillWeatherInfo(boolean isCTemp) {
        String lowTemp, highTemp, splitTemp[], splitStatus[], imgType, statDes;

        if (weatherInfo == null) {
            return;
        }

        if (isCTemp) {
            splitTemp = weatherInfo.getLowHighCTemp().split("-");
            lowTemp = splitTemp[0];
            highTemp = splitTemp[1];
            lbDegree.setText("<html>" + weatherInfo.getCurrentCTemp() + "&deg;C</html>");
            lbLowTemp.setText("<html>" + lowTemp + "&deg;</html>");
            lbHighTemp.setText("<html>" + highTemp + "&deg;</html>");
        } else {
            splitTemp = weatherInfo.getLowHighFTemp().split("-");
            lowTemp = splitTemp[0];
            highTemp = splitTemp[1];
            lbDegree.setText("<html>" + weatherInfo.getCurrentFTemp() + "&deg;F</html>");
            lbLowTemp.setText("<html>" + lowTemp + "&deg;</html>");
            lbHighTemp.setText("<html>" + highTemp + "&deg;</html>");
        }
        cityName = weatherInfo.getCityName();

        lbHumidity.setText(weatherInfo.getHumidity());
        lbCloud.setText(weatherInfo.getCloudTotal());
        lbWinSpeed.setText(weatherInfo.getWindSpeed());
        lbCityName.setText(cityName + ", " + weatherInfo.getCountryName());
        
        splitStatus = weatherInfo.getStatus().split("-");
        imgType = splitStatus[0];
        statDes = splitStatus[1];

        if (imgType.equals("1")) {
            lbWeatherImg3.setIcon(new FlatSVGIcon("quanlynganhang/icon/sunny.svg"));
        } else if (imgType.equals("2")) {
            lbWeatherImg3.setIcon(new FlatSVGIcon("quanlynganhang/icon/weather_cloudy.svg"));
        } else {
            lbWeatherImg3.setIcon(new FlatSVGIcon("quanlynganhang/icon/rainy.svg"));
        }
        
        lbWeatherStatus.setText(statDes);
    }

    private void enableForm(boolean isEnable) {
        txtWeatherSearch.setEnabled(isEnable);
        btnSearchCity.setEnabled(isEnable);
        btnReset.setEnabled(isEnable);
        spLoadWeather.setIndeterminate(!isEnable);
    }

    private void onTextChanged() {
        btnSearchCity.setEnabled(!txtWeatherSearch.getText().trim().isEmpty());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPBody = new javax.swing.JPanel();
        jPLich = new javax.swing.JPanel();
        jPThu = new javax.swing.JPanel();
        lbWeekday = new javax.swing.JLabel();
        jPSuKien = new javax.swing.JPanel();
        lbEvent = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPNgay = new javax.swing.JPanel();
        lbDay = new javax.swing.JLabel();
        jPThangVaNam = new javax.swing.JPanel();
        lbMonthAndYear = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        slideshow1 = new quanlynganhang.GUI.model.slider.Slideshow();
        jPTinTuc = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPGhiChu2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtWeatherSearch = new javax.swing.JTextField();
        btnSearchCity = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPWeatherInfo = new javax.swing.JPanel();
        jPShowInfo = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jP16 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        lbWeatherImg3 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lbWeatherStatus = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jP13 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lbDegree = new javax.swing.JLabel();
        btnSwitchTemp = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        lbHighTemp = new javax.swing.JLabel();
        lbLowTemp = new javax.swing.JLabel();
        lbHumidity = new javax.swing.JLabel();
        lbCloud = new javax.swing.JLabel();
        lbWinSpeed = new javax.swing.JLabel();
        lbCityName = new javax.swing.JLabel();
        jPLoadInfo = new javax.swing.JPanel();
        spLoadWeather = new quanlynganhang.GUI.model.spinner.SpinnerProgress();
        jPInitWeather = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setPreferredSize(new java.awt.Dimension(1220, 511));

        jPBody.setBackground(new java.awt.Color(102, 102, 102));

        jPLich.setBackground(new java.awt.Color(51, 204, 255));
        jPLich.setLayout(new java.awt.BorderLayout());

        lbWeekday.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        lbWeekday.setForeground(new java.awt.Color(255, 255, 255));
        lbWeekday.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbWeekday.setText("Hôm nay - Thứ sáu");

        javax.swing.GroupLayout jPThuLayout = new javax.swing.GroupLayout(jPThu);
        jPThu.setLayout(jPThuLayout);
        jPThuLayout.setHorizontalGroup(
            jPThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbWeekday, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPThuLayout.setVerticalGroup(
            jPThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbWeekday, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPLich.add(jPThu, java.awt.BorderLayout.PAGE_START);

        lbEvent.setForeground(new java.awt.Color(255, 255, 255));
        lbEvent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbEvent.setText("Tết Dương lịch");

        javax.swing.GroupLayout jPSuKienLayout = new javax.swing.GroupLayout(jPSuKien);
        jPSuKien.setLayout(jPSuKienLayout);
        jPSuKienLayout.setHorizontalGroup(
            jPSuKienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSuKienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbEvent, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPSuKienLayout.setVerticalGroup(
            jPSuKienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSuKienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbEvent, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPLich.add(jPSuKien, java.awt.BorderLayout.PAGE_END);

        jPanel7.setLayout(new java.awt.BorderLayout());

        lbDay.setFont(new java.awt.Font("Segoe UI", 1, 60)); // NOI18N
        lbDay.setForeground(new java.awt.Color(255, 255, 255));
        lbDay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDay.setText("10");

        javax.swing.GroupLayout jPNgayLayout = new javax.swing.GroupLayout(jPNgay);
        jPNgay.setLayout(jPNgayLayout);
        jPNgayLayout.setHorizontalGroup(
            jPNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNgayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbDay, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPNgayLayout.setVerticalGroup(
            jPNgayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNgayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbDay, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.add(jPNgay, java.awt.BorderLayout.PAGE_START);

        lbMonthAndYear.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lbMonthAndYear.setForeground(new java.awt.Color(255, 255, 255));
        lbMonthAndYear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMonthAndYear.setText("Tháng 4, 2024");

        javax.swing.GroupLayout jPThangVaNamLayout = new javax.swing.GroupLayout(jPThangVaNam);
        jPThangVaNam.setLayout(jPThangVaNamLayout);
        jPThangVaNamLayout.setHorizontalGroup(
            jPThangVaNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThangVaNamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMonthAndYear, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPThangVaNamLayout.setVerticalGroup(
            jPThangVaNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThangVaNamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMonthAndYear, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.add(jPThangVaNam, java.awt.BorderLayout.CENTER);

        jPLich.add(jPanel7, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slideshow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(slideshow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPTinTuc.setBackground(new java.awt.Color(56, 204, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Tin tức hôm nay");

        javax.swing.GroupLayout jPTinTucLayout = new javax.swing.GroupLayout(jPTinTuc);
        jPTinTuc.setLayout(jPTinTucLayout);
        jPTinTucLayout.setHorizontalGroup(
            jPTinTucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTinTucLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(407, Short.MAX_VALUE))
        );
        jPTinTucLayout.setVerticalGroup(
            jPTinTucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTinTucLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPGhiChu2.setBackground(new java.awt.Color(51, 204, 255));
        jPGhiChu2.setLayout(new java.awt.BorderLayout());

        txtWeatherSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtWeatherSearchKeyReleased(evt);
            }
        });

        btnSearchCity.setIcon(new FlatSVGIcon("quanlynganhang/icon/search_btn.svg"));
        btnSearchCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchCityActionPerformed(evt);
            }
        });

        btnReset.setText("Làm mới");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtWeatherSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearchCity, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 217, Short.MAX_VALUE)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtWeatherSearch)
                    .addComponent(btnSearchCity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addContainerGap())
        );

        txtWeatherSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onTextChanged();
            }
        });

        jPGhiChu2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPWeatherInfo.setLayout(new java.awt.CardLayout());

        jPShowInfo.setLayout(new java.awt.BorderLayout());

        jPanel14.setLayout(new java.awt.BorderLayout());

        jP16.setLayout(new java.awt.BorderLayout());

        lbWeatherImg3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbWeatherImg3.setIcon(new FlatSVGIcon("quanlynganhang/icon/weather_cloudy.svg"));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(lbWeatherImg3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbWeatherImg3, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addContainerGap())
        );

        jP16.add(jPanel18, java.awt.BorderLayout.PAGE_START);

        lbWeatherStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbWeatherStatus.setForeground(new java.awt.Color(255, 255, 255));
        lbWeatherStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbWeatherStatus.setText("Có mưa nhẹ");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbWeatherStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbWeatherStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jP16.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel14.add(jP16, java.awt.BorderLayout.CENTER);

        jPShowInfo.add(jPanel14, java.awt.BorderLayout.LINE_END);

        jPanel15.setLayout(new java.awt.BorderLayout());

        jP13.setLayout(new java.awt.BorderLayout());

        lbDegree.setFont(new java.awt.Font("Segoe UI", 1, 35)); // NOI18N
        lbDegree.setForeground(new java.awt.Color(255, 255, 255));
        lbDegree.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDegree.setText("<html>25&deg;C</html>");

        btnSwitchTemp.setIcon(new FlatSVGIcon("quanlynganhang/icon/weather_switch.svg"));
        btnSwitchTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSwitchTempActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbDegree, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSwitchTemp)
                .addContainerGap(291, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbDegree, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSwitchTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jP13.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        lbHighTemp.setForeground(new java.awt.Color(255, 255, 255));
        lbHighTemp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHighTemp.setIcon(new FlatSVGIcon("quanlynganhang/icon/temp_high.svg"));
        lbHighTemp.setText("<html>32&deg;</html>");
        lbHighTemp.setToolTipText("Nhiệt độ cao nhất");

        lbLowTemp.setForeground(new java.awt.Color(255, 255, 255));
        lbLowTemp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbLowTemp.setIcon(new FlatSVGIcon("quanlynganhang/icon/temp_low.svg"));
        lbLowTemp.setText("<html>10&deg;</html>");
        lbLowTemp.setToolTipText("Nhiệt độ thấp nhất");

        lbHumidity.setForeground(new java.awt.Color(255, 255, 255));
        lbHumidity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbHumidity.setIcon(new FlatSVGIcon("quanlynganhang/icon/weather_humidity.svg"));
        lbHumidity.setText("88%");
        lbHumidity.setToolTipText("Độ ẩm không khí");

        lbCloud.setForeground(new java.awt.Color(255, 255, 255));
        lbCloud.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbCloud.setIcon(new FlatSVGIcon("quanlynganhang/icon/weather_cloud.svg"));
        lbCloud.setText("1%");
        lbCloud.setToolTipText("Độ bao phủ mây");

        lbWinSpeed.setForeground(new java.awt.Color(255, 255, 255));
        lbWinSpeed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbWinSpeed.setIcon(new FlatSVGIcon("quanlynganhang/icon/weather_win_speed.svg"));
        lbWinSpeed.setText("10km/s");
        lbWinSpeed.setToolTipText("Tốc độ gió");

        lbCityName.setFont(new java.awt.Font("Segoe UI", 0, 22)); // NOI18N
        lbCityName.setForeground(new java.awt.Color(255, 255, 255));
        lbCityName.setText("(Chưa có)");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbHighTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbLowTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbHumidity, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbCloud, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbWinSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(lbCityName, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbHighTemp, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(lbLowTemp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbHumidity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbWinSpeed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbCloud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbCityName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jP13.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel15.add(jP13, java.awt.BorderLayout.CENTER);

        jPShowInfo.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPWeatherInfo.add(jPShowInfo, "jPShowInfo");

        spLoadWeather.setForeground(new java.awt.Color(255, 255, 0));
        spLoadWeather.setValue(50);
        spLoadWeather.setIndeterminate(true);

        javax.swing.GroupLayout jPLoadInfoLayout = new javax.swing.GroupLayout(jPLoadInfo);
        jPLoadInfo.setLayout(jPLoadInfoLayout);
        jPLoadInfoLayout.setHorizontalGroup(
            jPLoadInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPLoadInfoLayout.createSequentialGroup()
                .addContainerGap(290, Short.MAX_VALUE)
                .addComponent(spLoadWeather, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
        );
        jPLoadInfoLayout.setVerticalGroup(
            jPLoadInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPLoadInfoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(spLoadWeather, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPWeatherInfo.add(jPLoadInfo, "jPLoadInfo");

        jPInitWeather.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thông tin thời tiết hôm nay sẽ xuất hiện ở đây");
        jPInitWeather.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPWeatherInfo.add(jPInitWeather, "jPInitWeather");

        jPGhiChu2.add(jPWeatherInfo, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPBodyLayout = new javax.swing.GroupLayout(jPBody);
        jPBody.setLayout(jPBodyLayout);
        jPBodyLayout.setHorizontalGroup(
            jPBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPBodyLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPLich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPBodyLayout.createSequentialGroup()
                        .addComponent(jPTinTuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPGhiChu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(6, 6, 6))
        );
        jPBodyLayout.setVerticalGroup(
            jPBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPLich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPTinTuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPGhiChu2, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCityActionPerformed
        initWeather(txtWeatherSearch.getText().trim());
    }//GEN-LAST:event_btnSearchCityActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        initCalendar();

        if (!cityName.isEmpty()) {
            initWeather(cityName);
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnSwitchTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSwitchTempActionPerformed
        isCTemp = !isCTemp;
        fillWeatherInfo(isCTemp);
    }//GEN-LAST:event_btnSwitchTempActionPerformed

    private void txtWeatherSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtWeatherSearchKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSearchCityActionPerformed(null);
        }
    }//GEN-LAST:event_txtWeatherSearchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearchCity;
    private javax.swing.JButton btnSwitchTemp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jP13;
    private javax.swing.JPanel jP16;
    private javax.swing.JPanel jPBody;
    private javax.swing.JPanel jPGhiChu2;
    private javax.swing.JPanel jPInitWeather;
    private javax.swing.JPanel jPLich;
    private javax.swing.JPanel jPLoadInfo;
    private javax.swing.JPanel jPNgay;
    private javax.swing.JPanel jPShowInfo;
    private javax.swing.JPanel jPSuKien;
    private javax.swing.JPanel jPThangVaNam;
    private javax.swing.JPanel jPThu;
    private javax.swing.JPanel jPTinTuc;
    private javax.swing.JPanel jPWeatherInfo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lbCityName;
    private javax.swing.JLabel lbCloud;
    private javax.swing.JLabel lbDay;
    private javax.swing.JLabel lbDegree;
    private javax.swing.JLabel lbEvent;
    private javax.swing.JLabel lbHighTemp;
    private javax.swing.JLabel lbHumidity;
    private javax.swing.JLabel lbLowTemp;
    private javax.swing.JLabel lbMonthAndYear;
    private javax.swing.JLabel lbWeatherImg3;
    private javax.swing.JLabel lbWeatherStatus;
    private javax.swing.JLabel lbWeekday;
    private javax.swing.JLabel lbWinSpeed;
    private quanlynganhang.GUI.model.slider.Slideshow slideshow1;
    private quanlynganhang.GUI.model.spinner.SpinnerProgress spLoadWeather;
    private javax.swing.JTextField txtWeatherSearch;
    // End of variables declaration//GEN-END:variables
}
