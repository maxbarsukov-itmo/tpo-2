# Лабораторная работа 2

[![Build & Test](https://github.com/maxbarsukov-itmo/tpo-2/actions/workflows/build-and-test.yml/badge.svg)](https://github.com/maxbarsukov-itmo/tpo-2/actions/workflows/build-and-test.yml)

## Вариант `5157`

<img alt="anime" src="./.resources/yui-hirasawa.gif" height="300">

|.pdf|.docx|
|-|-|
| [report](./docs/report.pdf) | [report](./docs/report.docx) |

---

## Задание

Провести интеграционное тестирование программы, осуществляющей вычисление системы функций (в соответствии с вариантом).

Функция определяется как:

$$
f(x) =
\begin{cases}
\left(
  \left(
    \left( \tan(x) - \sec(x) \right) - \csc(x)
  \right)^{\2} - \tan(x)
\right)
\cdot
\left(
  \frac{\sin(x)}{\tan(x)} - \frac{\tan(x)}{\cos(x)}
\right)
& \text{при } x \leq 0
\\
\left(
  \left(
    \frac{(\log_2 x)^2}{\ln x} \bigg/ \log_3 x
  \right)^{\3}
\right)
\cdot
\left(
  \frac{\ln x}{
    \log_3 x \big/ \left(
      \frac{\log_{10} x}{\log_2 x}
    \right)
  }
\right)
& \text{при } x > 0
\end{cases}
$$

```text
x <= 0 : (((((tan(x) - sec(x)) - csc(x)) ^ 2) - tan(x)) * ((sin(x) / tan(x)) - (tan(x) / cos(x))))
x > 0 : (((((log_2(x) ^ 2) / ln(x)) / log_3(x)) ^ 3) * (ln(x) / (log_3(x) / (log_10(x) / log_2(x)))))
```

### Правила выполнения работы

1. Все составляющие систему функции (как тригонометрические, так и логарифмические) должны быть выражены через **базовые** (тригонометрическая зависит от варианта; логарифмическая – натуральный логарифм).
2. Структура приложения, тестируемого в рамках лабораторной работы, должна выглядеть следующим образом (пример приведён для базовой тригонометрической функции `sin(x)`):

<p align="center">
  <picture>
    <source
      srcset="./.resources/example-white.png"
      media="(prefers-color-scheme: dark)"
    />
    <source
      srcset="./.resources/example.png"
      media="(prefers-color-scheme: light), (prefers-color-scheme: no-preference)"
    />
    <img src="./.resources/example.png" alt="Пример для базовой тригонометрической функции sin(x))" height="350" />
  </picture>
</p>

3. Обе «базовые» функции (в примере выше – `sin(x)` и `ln(x)`) должны быть реализованы при помощи разложения в ряд с задаваемой погрешностью. Использовать тригонометрические / логарифмические преобразования для упрощения функций **запрещено**.
4. Для **каждого** модуля должны быть реализованы табличные заглушки. При этом, необходимо найти область допустимых значений функций, и, при необходимости, определить взаимозависимые точки в модулях.
5. Разработанное приложение должно позволять выводить значения, выдаваемое любым модулем системы, в `сsv` файл вида *«X, Результаты модуля (X)»*, позволяющее произвольно менять шаг наращивания `Х`. Разделитель в файле `csv` можно использовать произвольный.

### Порядок выполнения работы

1. Разработать приложение, руководствуясь приведёнными выше правилами.
2. С помощью `JUnit 5` разработать тестовое покрытие системы функций, проведя **анализ эквивалентности** и учитывая особенности системы функций. Для анализа особенностей системы функций и составляющих ее частей можно использовать сайт [www.wolframalpha.com](https://www.wolframalpha.com/).
3. Собрать приложение, состоящее из заглушек. Провести интеграцию приложения по 1 модулю, с обоснованием стратегии интеграции, проведением интеграционных тестов и контролем тестового покрытия системы функций.

### Отчёт по работе должен содержать

1. Текст задания, систему функций.
2. UML-диаграмму классов разработанного приложения.
3. Описание тестового покрытия с обоснованием его выбора.
4. Графики, построенные по `csv`-выгрузкам, полученным в процессе интеграции приложения.
5. Выводы по работе.

### Вопросы к защите лабораторной работы

1. Цели и задачи интеграционного тестирования. Расположение фазы интеграционного тестирования в последовательности тестов; предшествующие и последующие виды тестирования ПО.
2. Алгоритм интеграционного тестирования.
3. Концепции и подходы, используемые при реализации интеграционного тестирования.
4. Программные продукты, используемые для реализации интеграционного тестирования. Использование `JUnit` для интеграционных тестов.
5. Автоматизация интеграционных тестов. ПО, используемое для автоматизации интеграционного тестирования.

---

## Выполнение

Тригонометрические функции:

| Класс     | Зависимости   | Формула       |
|-----------|---------------|---------------|
| [`Sine`](./src/main/java/ru/itmo/qa/lab2/trig/Sine.java)      | —             | Ряд Тейлора   |
| [`Cosine`](./src/main/java/ru/itmo/qa/lab2/trig/Cosine.java)    | `Sine`          | `sin(π/2 - x)`  |
| [`Cosecant`](./src/main/java/ru/itmo/qa/lab2/trig/Cosecant.java)  | `Sine`          | `1/sin(x)`      |
| [`Secant`](./src/main/java/ru/itmo/qa/lab2/trig/Secant.java)    | `Cosine`        |  `1/cos(x)`     |
| [`Tangent`](./src/main/java/ru/itmo/qa/lab2/trig/Tangent.java)   | `Sine` + `Cosine` | `sin(x)/cos(x)` |
| [`Cotangent`](./src/main/java/ru/itmo/qa/lab2/trig/Cotangent.java) | `Sine` + `Cosine` | `cos(x)/sin(x)` |

Логарифмические функции:

| Класс | Зависимости | Формула | Особенности реализации |
|---|---|---|---|
| [`NaturalLogarithm`](./src/main/java/ru/itmo/qa/lab2/log/NaturalLogarithm.java) | — | Ряд Тейлора для `ln(1+x)` | Использует преобразование аргумента через `(x-1)/(x+1)` |
| [`BaseNLogarithm`](./src/main/java/ru/itmo/qa/lab2/log/BaseNLogarithm.java) | `NaturalLogarithm` | `logₐ(x) = ln(x)/ln(a)` | Поддерживает произвольное основание через конструктор |

---

## Полезные ссылки

| Ссылка | Описание |
|---|---|
| [github.com/RedGry/ITMO/TPO/ТПО 2 - Подготовка.pdf](https://github.com/RedGry/ITMO/blob/master/TPO/docs/%D0%A2%D0%9F%D0%9E%202%20-%20%D0%9F%D0%BE%D0%B4%D0%B3%D0%BE%D1%82%D0%BE%D0%B2%D0%BA%D0%B0.pdf) | Подготовка к защите ЛР 2 |
| [github.com/band-of-four/cheatsheets/testing/lab2.md](https://github.com/band-of-four/cheatsheets/blob/master/testing/lab2.md) | Ответы на вопросы с [se.ifmo.ru](https://se.ifmo.ru/courses/testing#labs) |
| [youtu.be/nU1Rvo8YyeY](https://www.youtube.com/watch?v=nU1Rvo8YyeY&ab_channel=SergeKlimenkov) | Лекция ТПО #3. Модульное и интеграционное тестирование |
| [katalon.com/blog/integration-testing](https://katalon.com/resources-center/blog/integration-testing) | What is Integration Testing? Definition, How-to, Examples |
| [guru99.com/equivalence-partitioning-boundary-value-analysis.html](https://web.archive.org/web/20250328095328/https://www.guru99.com/equivalence-partitioning-boundary-value-analysis.html) | Boundary Value Analysis and Equivalence Partitioning |
| [site.mockito.org](https://site.mockito.org/) | Примеры с `Mockito` |
| [www.baeldung.com/parameterized-tests-junit-5](https://www.baeldung.com/parameterized-tests-junit-5) | Guide to `JUnit 5` Parameterized Tests |
| [mathworld.wolfram.com/TaylorSeries.html](https://mathworld.wolfram.com/TaylorSeries.html) | Разложение тригонометрических функций в ряды Тейлора |
| [wikipedia.org/wiki/Natural_logarithm](https://en.wikipedia.org/wiki/Natural_logarithm#Series) | Ряды для натурального логарифма и преобразования между разными основаниями |

## Лицензия <a name="license"></a>

Проект доступен с открытым исходным кодом на условиях [Лицензии MIT](https://opensource.org/licenses/MIT). \
*Авторские права 2025 Max Barsukov*

**Поставьте звезду :star:, если вы нашли этот проект полезным.**
