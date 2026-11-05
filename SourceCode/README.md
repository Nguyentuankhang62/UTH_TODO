# UTH Todo V2 — Navigation + OOP

Ứng dụng Android Kotlin + Jetpack Compose tái tạo 4 màn hình trong ảnh tham khảo: Splash Screen và 3 bước Onboarding.

## Chức năng hiện có

- Splash Screen màu xanh, tự chuyển sau 1,8 giây.
- Ẩn thanh trạng thái và thanh điều hướng hệ thống để phần giao diện chiếm trọn màn hình giống ảnh tham khảo.
- Onboarding 1: “Mọi việc, gọn trong một chỗ”.
- Onboarding 2: “Chia theo Học tập và Cuộc sống”.
- Onboarding 3: “Nhắc đúng lúc”.
- Nút **Bỏ qua** chuyển đến màn hình đích.
- Nút **Tiếp tục** chuyển lần lượt qua từng trang.
- Nút **Bắt đầu ngay** chuyển đến màn hình đích.
- Chỉ báo trang hiện tại thay đổi theo bước onboarding.
- Logo UTH Todo được đồng bộ theo nhận diện: biểu tượng checklist, chữ UTH đậm và Todo màu xanh.
- Cỡ chữ tiêu đề, mô tả và nút được căn lại để rõ, cân đối và gần ảnh mẫu hơn.
- Màu xanh chính: `#2F5BEA`; màu chữ chính: `#1C1C1E`.

> Màn hình Home hiện là màn hình đích tạm để xác nhận Navigation. Ảnh tham khảo chưa có thiết kế Home nên không tự ý thêm giao diện Todo vào phần này.

## Công nghệ

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Android Gradle Plugin 8.11.1
- Kotlin 2.2.21
- Gradle 8.13
- Compile SDK 36, Minimum SDK 24

## Cấu trúc project

```text
UTHTodo/
├── app/
│   └── src/main/
│       ├── java/com/example/uthtodo/
│       │   ├── MainActivity.kt
│       │   ├── data/
│       │   │   ├── model/
│       │   │   │   ├── AppContent.kt
│       │   │   │   ├── OnboardingPage.kt
│       │   │   │   └── OnboardingIllustration.kt
│       │   │   └── repository/
│       │   │       └── OnboardingRepository.kt
│       │   ├── navigation/
│       │   │   └── AppNavigation.kt
│       │   └── ui/
│       │       ├── components/
│       │       │   └── BrandComponents.kt
│       │       ├── screens/
│       │       │   ├── SplashScreen.kt
│       │       │   ├── OnboardingScreen.kt
│       │       │   └── HomeScreen.kt
│       │       └── theme/
│       │           └── AppColors.kt
│       └── res/values/
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Áp dụng OOP

| Đặc trưng | Vị trí áp dụng |
|---|---|
| Đóng gói | `AppContent`, `OnboardingPage`, các lớp minh họa và repository phân chia dữ liệu theo trách nhiệm |
| Trừu tượng | `OnboardingIllustration` là lớp trừu tượng định nghĩa hành vi `Draw()` |
| Kế thừa / triển khai | `ChecklistIllustration`, `CategoryIllustration`, `ReminderIllustration` kế thừa lớp trừu tượng minh họa |
| Đa hình | `OnboardingScreen` gọi `page.illustration.Draw()` mà không cần biết trang đang dùng loại minh họa nào |

Dữ liệu tiêu đề và mô tả được trả về từ `DefaultOnboardingRepository`. Các chuỗi dùng chung nằm trong `AppContent`. Màu giao diện được gom trong `AppColors`. Logo và kiểu chữ thương hiệu được tái sử dụng từ `BrandComponents.kt`.

