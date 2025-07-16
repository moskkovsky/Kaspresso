# Kaspresso
> [!TIP]
> Kaspresso — это фреймворк для тестирования пользовательского интерфейса (UI) приложений под Android

## Введение
Для создания экранов с элементами, необходимо насследоваться от **Kscreen**() и переопределить 2 метода: **layoutId** (id макета) и **viewClass**(класс activity).

Для нахождения элементов на экране можно воспользовться **LayoutInspector.**
```
object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val simpleActivityButton = KButton { withId(R.id.simple_activity_btn) }
}
```
Перед запуском необходимо открыть начальный экран - например MainActivity.
```
@get:Rule
val activityRule = activityScenarioRule<MainActivity>()
```

**targetContext** - обращаемся к тестируемому приложению.
```
val intent = device.targetContext.packageManager.getLaunchIntentForPackage(GOOGLE_PLAY_PACKAGE)
```

## Основные adb команды
> [!TIP]
> ADB - это инструмент командной строки, который позволяет взаимодействовать с девайсом посредством различных команд. Для того, чтобы тесты, которые работают с adb, могли выполняться, необходимо запустить adb-server

* `performAdb` выполняет команду adb
* `performShell` выполняет команду shell
* `performCmd` выполняет команду командной строки
```
adbServer.performAdb("devices")
```
| Команда  | Что делает |
| ------------- |:-------------:|
| adb devices      | Какие устройства сейчас подключены к adb     |
| adb shell      | для выполнения Linux-команд на устройстве(открывает консоль)     |
| adb reboot      | перезагрузка устройства     |
| adb install app.apk | установка apk приложения |
| adb shell pm list packages | вывод установленных приложений |

[Больше примеров](https://dev.to/plotegor/adb-fastboot-3mgf)

## steps(before, after) и scenario
> [!WARNING]
> Чтобы использовать step-ы, необходимо вызвать метод run {} и в фигурных скобках перечислить все шаги, которые будут выполнены во время теста.
```
@Test
fun checkCallIfPermissionDenied() = run {
        step("Открываем экран Авторизации") {
        }
}
```
**before, after** - вспомогательные методы, которые можно устанавливать перед и после теста.
```
 @Test
    fun test() {
        before {
            /**
             * Выполняется перед тестом
             */
        }.after {
            /**
             * Выполняется после теста
             */
        }.run {
            step("Открываем экран Авторизации") {
            }
        }
    }
```
> [!IMPORTANT]
> Сценарии – это классы, которые позволяют объединить в себе несколько step-ов.
```
class LoginScenario : Scenario() {
    override val steps: TestContext<Unit>.() -> Unit = {
        step("Открываем экран Авторизации") {
            
        }
        step("Проверяем видимость элементов) {

        }
    }
}
```
## Логирование
> [!TIP]
> У класса Log необходимо вызвать один из публичных статических методов: i (info), d (debug), w (warning), e (error)
* Debug — сообщения для отладки программы
* Error — серьезные ошибки, возникшие во время работы программы
* Warning — предупреждения. Программа может продолжать работу, но рекомендуется обратить внимание на какую-то проблему
* Info — простые сообщения, содержащие различного рода информацию. Система работает нормально

**UiTestLogger** - вывод сообщений в лог с тэгом "KASPRESSO_TEST" реализован под капотом. В самих тестах вам достаточно обратиться к объекту testLogger, вызвав метод с необходимым уровнем логирования.

```
testLogger.i("KASPRESSO","Авторизация с данными: Username: $username, Password: $password")
```

## Объект device
С помощью объекта device можно:
* device.targetContext.packageName - имя пакета приложения
* device.keyboard.typeText("text")/sendEvent(KeyEvent.KEYCODE_S) - ввод текста через  клавиатуру
* device.network.toggleWiFi(false) - В качестве параметра передаем тип boolean, false если хотим выключить Wifi, true - если хотим включить
* device.exploit.setOrientation(Exploit.DeviceOrientation.Landscape / (Exploit.DeviceOrientation.Portrait)) - переворачивать экран
* device.permissions.allowViaDialog/denyViaDialog() - разрешить функцию на устройстве
* device.phone.cancelCall("phoneNumber") - звонить, сбрасывать звонок

## Скриншоты
> [!TIP]
> В Kaspresso есть возможность во время теста делать скриншоты на любом шаге
Для этого достаточно вызвать метод **device.screenshots.take("file_name")**
