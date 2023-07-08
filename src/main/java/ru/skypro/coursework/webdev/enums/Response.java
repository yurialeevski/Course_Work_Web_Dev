package ru.skypro.coursework.webdev.enums;

public enum Response {
    CREATED ("Лот успешно создан"),
    STARTED ("Лот переведен в статус начато"),
    STOPPED ("Лот перемещен в статус остановлен"),
    ERROR ("Лот передан с ошибкой"),
    ERROR_404 ("Лот не найден");

    private String title;

    Response(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return  title ;
    }
}
