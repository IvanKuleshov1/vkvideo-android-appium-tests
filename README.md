Автотест для Android-приложения [VK Видео](https://play.google.com/store/apps/details?id=com.vk.vkvideo) `com.vk.vkvideo`

Проверяет воспроизведение видео:

положительный сценарий — видео воспроизводится при активном интернет-соединении;

негативный сценарий — при отключении сети воспроизведение не подтверждается и появляется индикатор загрузки.

- Java 22
- Appium 8.6.0
- JUnit 5
- Android 11 (API 30)



для запуска нужно:

- поднять appium server на `http://127.0.0.1:4723`
- запустить сам эмулятор (android 11)
- установить `com.vk.vkvideo`

запуск теста командой:
`mvn clean test -Dtest=VkVideoPlaybackTest`
