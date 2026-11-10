# UTH Todo

Ứng dụng Android Kotlin + Jetpack Compose
## Công nghệ

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- Android Gradle Plugin 8.11.1
- Kotlin 2.2.21
- Gradle 8.13
- Compile SDK 36, Minimum SDK 24

## Cấu hình Firebase

Tải `google-services.json` cho ứng dụng Android từ Firebase Console và đặt tại `app/google-services.json` trước khi build. File cấu hình này không được commit lên repository.

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
