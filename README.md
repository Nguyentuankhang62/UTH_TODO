# UTH Todo

Ứng dụng Android quản lý công việc, xây dựng bằng Kotlin và Jetpack Compose.

## Chức năng

- Màn hình chào mừng và hướng dẫn sử dụng.
- Đăng nhập bằng email, Google hoặc chế độ khách qua Firebase Authentication.
- Xem danh sách công việc; tạo, cập nhật, xóa và đánh dấu hoàn thành.
- Phân loại công việc, mức độ ưu tiên và công việc con.
- Màn hình chi tiết công việc, thông báo, hồ sơ và cài đặt.
- Lưu dữ liệu công việc bằng Cloud Firestore.

## Công nghệ

- Kotlin, Jetpack Compose, Material 3 và Navigation Compose.
- Firebase Authentication và Cloud Firestore.
- Gradle 8.13, Android Gradle Plugin 8.11.1, JDK 17.
- Minimum SDK 24; Compile SDK 36.

## Chạy ứng dụng

1. Mở thư mục `SourceCode` trong Android Studio.
2. Dùng JDK 17 và chờ Gradle Sync hoàn tất.
3. Tải `google-services.json` cho ứng dụng Android từ Firebase Console, đặt tại `SourceCode/app/google-services.json`, rồi bật Firebase Authentication và Cloud Firestore cho dự án.
4. Chạy cấu hình `app` trên thiết bị hoặc emulator.

`google-services.json` không được lưu trong repository. Cần cấu hình Firebase riêng để chạy các chức năng đăng nhập và lưu dữ liệu.

Chạy unit test từ thư mục `SourceCode`:

```shell
./gradlew test
```

Trên Windows:

```powershell
.\gradlew.bat test
```

## Cấu trúc

- `SourceCode/app/src/main/java/com/example/uthtodo/data/` — model và repository.
- `SourceCode/app/src/main/java/com/example/uthtodo/navigation/` — điều hướng ứng dụng.
- `SourceCode/app/src/main/java/com/example/uthtodo/ui/` — màn hình, component và giao diện.
- `SourceCode/app/src/test/` — unit test.

Tài liệu về kiến trúc và áp dụng OOP: [SourceCode/README.md](SourceCode/README.md).
