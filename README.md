# Игра-викторина "10 миллионов"
Приложение позволяет пользователю играть в викторину "10 миллионов".
Пользователю предлагается случайный набор из 10 вопросов, формируемый из общего списка вопросов (не менее 50 вопросов).
В начале игры пользователю начисляется 10 000 очков.
Для каждого вопроса предусмотрены 4 варианта ответа, 1 из которых правильный. На ответ даётся ограниченное время.
Игрок делает ставки, распределяя имеющиеся у него очки между предложенными вариантами ответа в зависимости от степени уверенности. Распределяются все очки в копилке игрока. Сумма, соответствующая ставке на ответ, оказавшийся правильным, остаётся у игрока, а остальные ставки "сгорают". Игра продолжается, пока игрок не ответит на все предложенные вопросы или сумма в его копилке не станет равной нулю.
Игрок, одержавший победу (т.е. ответивший на все вопросы) может добавить информацию об игре в общий рейтинг. Игроки идентифицируются уникальным именем.

# Требования к выполнению
* В корне проекта приложения должен располагаться файл README.txt с описанием задания.
* Приложение должно выполнять все функции, описанные в задании к лабораторной работе.
* Приложение не должно содержать ошибок.
* Приложение должно корректно обрабатывать неправильный ввод от пользователя.
* Архитектура приложения должна соответствовать шаблону Model-View-ViewModel.
* Навигация в приложении должна быть построена с помощью редактора навигации.
* Для работы с базой данных (если она необходима) приложение должно использовать Room.
* Приложение должно сохранять состояние экранов при смене ориентации.
* Приложение должно обладать понятным интерфейсом: показывать помощь по использованию, для ввода данных приложение должно показывать приглашение с сообщением о типе вводимых данных (например, если в поле необходимо ввести имя пользователя, необходимо добавить соответствующую подсказку к этому полю).
* Все отображаемые в приложении строки должны быть интернационализированы и располагаться в соответствующих файлах ресурсов: res/values/strings.xml (для строк на английском языке) и res/values-ru/strings.xml (для строк на русском).
