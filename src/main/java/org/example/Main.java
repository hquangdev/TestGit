package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "F:\\web_edit\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("file:F:/web_edit/HoangKhaiQuang_435/src/main/resources/home.html");

        // Test Họ và tên
        testCase(driver, "Quang", "quang@gmail.com", "hài lòng", "cần làm tốt hơn", "có", true, "Form hợp lệ!");
        pause(3000);

        //Test Tích chọn đồng ý bắt buộc
        testCase(driver, "Lê Hồng Phong", "phong@gmail.com", "rất hài lòng", "Xuất sắc", "có", true, "Form hợp lệ!");
        pause(3000);

        testCase(driver, "2484", "quang@gmail.com", "hài lòng", "cần làm tốt hơn", "có", false, "Tên không được nhập full số!");
        pause(3000);

        testCase(driver, "Trần Văn Tùng", "quang@gmail.com", "hài lòng", "cần làm tốt hơn", "có", true, "Form hợp lệ.");
        pause(3000);

        testCase(driver, "Quang22", "quang@gmail.com", "hài lòng", "cần làm tốt hơn", "có", true, "Form hợp lệ vừa tets.");
        pause(3000);

        testCase(driver, "Trần Văn Tùng123", "quang@gmail.com", "hài lòng", "cần làm tốt hơn", "có", false, "Tên chỉ được phép nhập chữ.");
        pause(3000);

        testCase(driver, "Trần Văn Tùng#@", "quang@gmail.com", "hài lòng", "cần làm tốt hơn", "có", false, "Tên không có dấu kí tự đặc biệt.");
        pause(3000);

        testCase(driver, "TH", "quang@gmail.com", "hài lòng", "cần làm tốt hơn", "có", false, "Tên phải có ít nhất 3 ký tự trở lên.");
        pause(3000);

        //Test Email
        testCase(driver, "Nguyễn Văn An", "", "rất hài lòng", "cần làm tốt hơn", "không", false, "Vui lòng nhập email.");
        pause(3000);

        testCase(driver, "Nguyễn Thị Bình", "tranthibinh123@gmail.com", "không hài lòng", "cần làm tốt hơn", "không", true, "Form hợp lệ!");
        pause(3000);

        testCase(driver, "Nguyễn Thị Bình", "tranthibinh###@gmail.com", "không hài lòng", "cần làm tốt hơn", "không", false, "Email không được phép nhập kí tự đặc biệt!");
        pause(3000);


        //Test Đánh giá tổng quát

        testCase(driver, "Nguyễn Thị Bình", "tranthibinh123@gmail.com", "", "cần làm tốt hơn", "không", false, "Bạn chưa chọn Đánh giá tổng quát!");
        pause(3000);

        testCase(driver, "Nguyễn Thị Bình", "tranthibinh123@gmail.com", "Cải thiện thêm", "cần làm tốt hơn", "không", true, "Form hợp lệ");
        pause(3000);

        //Test Đề xuất cải thiện
        testCase(driver, "Nguyễn Văn An", "an@gmail.com", "bình thường", "", "", false, "Vui lòng chọn một tùy chọn trong 'Bạn có giới thiệu chúng tôi với người khác không?'.");
        pause(3000);

        testCase(driver, "Nguyễn Thị Bình", "tranthibinh123@gmail.com", "Cải thiện thêm234", "cần làm tốt hơn", "không", true, "Form hợp lệ");
        pause(3000);

        testCase(driver, "Nguyễn Văn Hải", "hai@gmail.com", "bình thường", "Đây là đề xuất quá dài và có thể vượt qua giới hạn hợp lý của một trường văn bản. Hãy kiểm tra cách hệ thống xử lý lỗi nếu điều này xảy ra.", "không", true, "Form hợp lệ!");
        pause(3000);


        //Test Bạn có giới thiệu tôi với người khác
        testCase(driver, "Lê Hồng Phong", "phong@gmail.com", "rất hài lòng", "Xuất sắc", "", false, "Bạn chưa chọn giới thiệu tôi với người khác.");
        pause(3000);

        testCase(driver, "Lê Hồng Phong", "phong@gmail.com", "rất hài lòng", "Xuất sắc", "có", true, "Form hợp lệ!");
        pause(3000);

        //Test Tích chọn đồng ý bắt buộc
        testCase(driver, "Lê Hồng Phong", "phong@gmail.com", "rất hài lòng", "Xuất sắc", "có", true, "Form hợp lệ!");
        pause(3000);


    }

    private static void testCase(WebDriver driver, String hoten, String email, String danhgia, String dexuat, String gioithieu, boolean isValid, String expectedOutput) {

        driver.findElement(By.id("hoten")).clear();
        driver.findElement(By.id("hoten")).sendKeys(hoten);

        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(email);

        driver.findElement(By.id("danhgia")).sendKeys(danhgia);
        driver.findElement(By.id("dexuat")).clear();
        driver.findElement(By.id("dexuat")).sendKeys(dexuat);

        if ("có".equals(gioithieu)) {
            driver.findElement(By.id("gioithieu")).click();
        } else if ("không".equals(gioithieu)) {
            driver.findElement(By.id("recommend")).click();
        }

        WebElement checkbox = driver.findElement(By.id("khuyenmai"));
        if (isValid && !checkbox.isSelected()) {
            checkbox.click();
        }

        driver.findElement(By.id("check")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));

        WebElement resultElement = driver.findElement(By.id("result"));
        String actualOutput = resultElement.getText();

        // Display result on a single line
        if (actualOutput.contains(expectedOutput)) {
            System.out.println("Test thành công: " + hoten + ", Email: " + email + ", Đánh giá: " + danhgia + ", Đề xuất: " + dexuat + ", Kết quả: " + actualOutput);

            // Lưu vào cơ sở dữ liệu nếu hợp lệ
            if (isValid) {
                saveToDatabase(hoten, email, danhgia, dexuat, gioithieu, checkbox.isSelected());
            }
        } else {
            System.out.println("Test thất bại: " + hoten + ", Email: " + email + ", Đánh giá: " + danhgia + ", Đề xuất: " + dexuat + ", Lỗi là: " + expectedOutput );
        }
    }

    // Lưu vào DB
    private static void saveToDatabase(String hoten, String email, String danhgia, String dexuat, String gioithieu, boolean khuyenmai) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/springdb";
        String jdbcUsername = "root";
        String jdbcPassword = "1111";

        String sql = "INSERT INTO test_kh (hoten, email, danhgia, dexuat, gioithieu, khuyenmai) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the parameters for the query
            statement.setString(1, hoten);
            statement.setString(2, email);
            statement.setString(3, danhgia);
            statement.setString(4, dexuat);
            statement.setString(5, gioithieu);
            statement.setBoolean(6, khuyenmai);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dữ liệu đã được lưu vào cơ sở dữ liệu.");
            } else {
                System.out.println("Không có dữ liệu nào được lưu.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
