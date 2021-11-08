# Hidration Reminder

O aplicativo ajuda o usuário a controlar a quantidade de água bebida por dia, através de um cálculo
utilizando peso do usuário. Na configuração do app é solicitado além do peso a quantidade que usuário
bebe por vez, a hora que usuário acorda e dorme, com isso, o app através de notificações de hora em hora
chama atenção do usuário para que ele possa beber água e registra esse processo em um histórico,
calculando quanto mls o usuário já bebeu.

## Linguagens e Tecnologias

![Kotlin](https://img.shields.io/badge/-KOTLIN-orange?style=flat-square&logo=kotlin&logoColor=white) ![Android](https://img.shields.io/badge/-ANDROID-green?style=flat-square&logo=android&logoColor=white)
- [MVVM]
Arquitetura utilizada
- [ROOM]
Utilizado para persistência dos dados no SQLITE
- [COROUTINES]
Necessário para executar métodos de forma assíncrona ex: buscar dados no sqlite
- [FLOW]
Necessário para receber valores sequencias em tempo real, utilizado com o COROUTINES
- [KOIN]
Necessário para injeção de dependência.
- [LIFECYCLES]
Necessário para obter o estado dos fragments, permitindo que outros componentes observem esse estado.
- [ALARM_MANAGER]
Necessário para executar tarefas com horários específico e fora da vida útil do app.

- [DATASTORE]
Necessário para armazenar pares de chave valor ou objetos tipados, utilizado para substituir o SharedPreferences.


   [MVVM]: <https://developer.android.com/jetpack/guide>
   [ROOM]:<https://developer.android.com/training/data-storage/room>
   [COROUTINES]:<https://developer.android.com/kotlin/coroutines?hl=pt-br>
   [FLOW]:<https://developer.android.com/kotlin/flow?hl=pt-br>
   [KOIN]:<https://insert-koin.io/>
   [LIFECYCLES]:<https://developer.android.com/topic/libraries/architecture/lifecycle>
   [ALARM_MANAGER]:<https://developer.android.com/reference/android/app/AlarmManager>
   [DATASTORE]:<https://developer.android.com/topic/libraries/architecture/datastore?hl=pt-br>

## Observações sobre as Notificações

### Notificações
Para inflar as notificações eu poderia ter utilizado o work manager, porem, ele é bastante influenciado pela bateria do aparelho, então optei por utilizar o Alarme Manager para gerenciar as notificações, pois, o Alarm Manager é mais firme em relação ao horário

   ```kotlin
    val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(EXTRA_NOTIFICATION_ID, id)
        val pendingIntent = PendingIntent.getBroadcast(
            context, id, intent,
            flag
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            date.formatMilliSeconds(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    ```

Como podem ver acima estou utilizando método setRepepating para configurar um alarme com o intervalo
de 1 dia para cada notificação. Ao chegar a hora agenda o Notification Receiver é acionado e inflando
a notificação.

#### Mas o que é um receiver?
Receiver é um receptor ele pode receber intents mesmo que o app não esteja executando no momento. Neste
app utilizei 2 receivers uma para ficar buscando e inflando as notificações no momento adequando e outro
para ao dispositivo fazer um reboot ele iniciar e reagendar os alarmes para inflar as notificações, pois,
ao reiniciar o dispositivo elas são zeradas.
Como o receiver recebe dados do sistema e por fora da aplicação, temos que declarar no Manifest

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        package="br.com.jose.hydrationreminder">

        <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>

        <application
            android:name="br.com.jose.hydrationreminder.App"
            android:allowBackup="true"
            android:icon="@mipmap/ic_bottle"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_bottle_round"
            android:supportsRtl="true"
            android:theme="@style/Theme.HidrateReminder">

            <activity
                android:name="br.com.jose.hydrationreminder.ui.SplashScreenActivity"
                android:exported="true"
                android:noHistory="true"
                android:screenOrientation="portrait">
                <intent-filter>
                    <action android:name="android.intent.action.MAIN" />

                    <category android:name="android.intent.category.LAUNCHER" />
                </intent-filter>
            </activity>
            <activity
                android:name="br.com.jose.hydrationreminder.ui.MainActivity"
                android:exported="true"
                android:label="@string/app_name"
                android:screenOrientation="portrait"/>
            <receiver android:name=".notifications.NotificationReceiver" android:enabled="true"/>
            <receiver android:name=".notifications.BootNotificationReceiver"
                android:enabled="true"
                android:label="NotificationReceiver"
                android:permission="android.permission.RECEIVE_BOOT_COMPLETED"
                android:exported="false">
                <intent-filter>
                    <action android:name="android.intent.action.BOOT_COMPLETED"/>
                    <action android:name="android.intent.action.QUICKBOOT_POWERON" />
                </intent-filter>
            </receiver>
        </application>

    </manifest>
    ```
Repare que BootNotificationReceiver precisa da permissao RECEIVE_BOOT_COMPLETED, essa permissao vai liberar o app para realizar o reagendamento dos alarmes toda vez que celular ligar novamente.

#### Animações numéricas
Utilizei uma animação acionada quando o fragment de hidratação é chamado e quando o botão de hidratação
é clicado. Nesse momento a animação vai adicionando de 1 em 1 até o total configurado no app, ex: Bebidos:
0 a 300 ml ou a meta: 0 ao valor calculado pelo app. Deixando o app mais fluido e dinâmico.
Mas como se faz isso?

```kotlin
fun TextView.addNumberDrink(drunk: Int, quantity: Int) {
    val animator = ValueAnimator.ofInt(drunk, quantity)
    animator.duration = 1000
    animator.addUpdateListener {
        this.text = it.animatedValue.toString()
    }
    animator.start()
}
```
Utilizando o ValueAnimator, como podemos ver acima, o método ofInt espera o valor inicial e valor final
de até onde a animação deve acontecer, também podemos controlar o tempo como o método auxiliar duration.
Dentro do AddUpdaterListener vai acontecendo a adição dos valores.