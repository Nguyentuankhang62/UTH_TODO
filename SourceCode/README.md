# UTH Todo

Ứng dụng Android quản lý công việc, phát triển bằng Kotlin và Jetpack Compose.

## Chức năng chính

- Hướng dẫn sử dụng qua màn hình onboarding.
- Đăng nhập bằng email, Google hoặc chế độ khách với Firebase Authentication.
- Tạo, chỉnh sửa, xóa và đánh dấu hoàn thành công việc.
- Phân loại công việc, chọn mức độ ưu tiên và quản lý công việc con.
- Xem chi tiết công việc, thông báo, hồ sơ và cài đặt.
- Lưu dữ liệu công việc với Cloud Firestore.

## Công nghệ

- Kotlin, Jetpack Compose, Material 3, Navigation Compose.
- Firebase Authentication và Cloud Firestore.
- Android Gradle Plugin 8.11.1, Gradle 8.13, JDK 17.
- Compile SDK 36, Minimum SDK 24.

## Cấu hình và chạy

1. Mở thư mục này trong Android Studio.
2. Cấu hình JDK 17 và chờ Gradle Sync.
3. Tải `google-services.json` cho ứng dụng Android từ Firebase Console, đặt tại `app/google-services.json`, rồi bật Firebase Authentication và Cloud Firestore trong dự án Firebase.
4. Chạy cấu hình `app` trên thiết bị hoặc emulator.

Không commit `google-services.json`; file này được loại trừ bởi `.gitignore`. Các chức năng Firebase cần cấu hình dự án Firebase riêng.

Chạy unit test bằng `.\gradlew.bat test` trên Windows hoặc `./gradlew test` trên macOS/Linux.

## Cấu trúc mã nguồn

- `app/src/main/java/com/example/uthtodo/data/model/` — model công việc, hồ sơ và nội dung onboarding.
- `app/src/main/java/com/example/uthtodo/data/repository/` — repository cho onboarding, xác thực và công việc.
- `app/src/main/java/com/example/uthtodo/navigation/` — khai báo điều hướng và kết nối các màn hình.
- `app/src/main/java/com/example/uthtodo/ui/components/` — component giao diện có thể tái sử dụng.
- `app/src/main/java/com/example/uthtodo/ui/screens/` — các màn hình của ứng dụng.
- `app/src/test/` — unit test.

## Áp dụng OOP

| Đặc trưng | Ví dụ trong mã nguồn |
|---|---|
| Đóng gói | `Task`, `UserProfile`, `OnboardingPage` đóng gói dữ liệu theo từng model. |
| Trừu tượng | `OnboardingIllustration` định nghĩa hành vi vẽ minh họa. |
| Kế thừa | `ChecklistIllustration`, `CategoryIllustration` và `ReminderIllustration` kế thừa `OnboardingIllustration`. |
| Đa hình | `OnboardingScreen` gọi `page.illustration.Draw()` mà không cần phụ thuộc vào lớp minh họa cụ thể. |
| Phân chia trách nhiệm | `OnboardingRepository`, `FirebaseAuthRepository` và `TaskRepository` tách biệt các thao tác dữ liệu theo chức năng. |

Unit test onboarding nằm tại `app/src/test/java/com/example/uthtodo/data/repository/OnboardingRepositoryTest.kt`.
