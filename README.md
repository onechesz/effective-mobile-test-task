<h1>Веб-приложение, реализованное в процессе выполнение тестового задания на позицию Junior Java-разработчика в компанию https://effective-mobile.ru/.</h1>

<h2>О проекте</h2>

<h3>Описание</h3>

<p>Проект представляет собой RESTful-приложение, которое позволяет пользователям создавать, обновлять, удалять и просматривать посты, отправлять, отменять, отклонять и принимать заявки в друзья, отправлять и получать сообщения и просматривать посты друзей.</p>

<h2>Разработка</h2>

<h3>Технологии</h3>

<p>Согласно техническому заданию, необходимо было использовать обязательно такие вещи как Java, Spring, PostgreSQL или MySQL, Spring Security, Swagger или OpenAPI.</p>
<p>Так как я привык работать с PGSQL, я выбрал его, а не MySQL. То же самое и со Swagger. К тому же решил воспользоваться дополнительными удобными зависимостями.</p>
<p>Список технологий, использованных в процессе разработки:</p>
<ul>
    <li>Java (corretto-18.0.2)</li>
    <li>Maven</li>
    <li>Spring (Security, Validation, Web, PostgreSQL Driver, Data JPA, JWT), ModelMapper, Swagger</li>
    <li>PostgreSQL</li>
</ul>

<h3>Процесс</h3>

<p>Приложение разрабатывалось мной в IntelliJ IDEA. Я коммитил и описывал каждый этап.</p>

<h2>Результаты</h2>

<h3>Сводка</h3>

<p>Ссылки:</p>
<ul>
    <li><a href="https://docs.google.com/document/d/18OnbSqL_l0kLyg21ImBYulsfnLIOr7ov/edit?usp=sharing&ouid=101662408797857531787&rtpof=true&sd=true">оригинальное ТЗ</a></li>
</ul>

<h3>Запуск</h3>
<p>Порядок действий для запуска приложения:</p>
<ol>
    <li>Скачать проект и распаковать архив</li>
    <li>Создать базу данных в PostgreSQL и изменить (при необходимости) свойства в файле <strong>application.properties</strong></li>
    <li>Воссоздать структуру БД при помощи скрипта <strong>effective-mobile-test-task-db.sql</strong> (про Liquibase поздно вспомнил)</li>
    <li>Запустить программу при помощи функции main, находящейся в классе <strong>EffectiveMobileTestTaskApplication</strong></li>
</ol>

<p>Для перехода в Swagger-UI, откройте в браузере: <code>корневая-ссылка-проекта/swagger-ui.html</code></p>