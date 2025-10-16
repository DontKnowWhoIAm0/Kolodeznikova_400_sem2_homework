package ru.kpfu.itis.Kolodeznikova.entity.enums;

/**
 * Enum represents all available sports that a user can choose when looking for a training partner.
 * Each constant includes the sport name in Russian for display purposes.
 */

public enum Sports {
    RUNNING("Бег"),
    GYM("Тренажерный зал"),
    SWIMMING("Плавание"),
    CYCLING("Велоспорт"),
    FOOTBALL("Футбол"),
    BASKETBALL("Баскетбол"),
    VOLLEYBALL("Волейбол"),
    TENNIS("Теннис"),
    TABLE_TENNIS("Настольный теннис"),
    BOXING("Бокс"),
    WRESTLING("Борьба"),
    JUDO("Дзюдо"),
    KARATE("Каратэ"),
    TAEKWONDO("Тхэквондо"),
    HOCKEY("Хоккей"),
    FIELD_HOCKEY("Хоккей на траве"),
    BASEBALL("Бейсбол"),
    CRICKET("Крикет"),
    GOLF("Гольф"),
    RUGBY("Регби"),
    HANDBALL("Гандбол"),
    BADMINTON("Бадминтон"),
    ARCHERY("Стрельба из лука"),
    SHOOTING("Стрельба"),
    FENCING("Фехтование"),
    SKIING("Лыжный спорт"),
    SNOWBOARDING("Сноуборд"),
    SKATING("Конькобежный спорт"),
    FIGURE_SKATING("Фигурное катание"),
    SURFING("Сёрфинг"),
    SAILING("Парусный спорт"),
    ROWING("Гребля"),
    CLIMBING("Скалолазание"),
    WEIGHTLIFTING("Тяжёлая атлетика"),
    POWERLIFTING("Пауэрлифтинг"),
    CROSSFIT("Кроссфит"),
    GYMNASTICS("Гимнастика"),
    RHYTHMIC_GYMNASTICS("Художественная гимнастика"),
    AEROBICS("Аэробика"),
    DANCING("Спортивные танцы"),
    EQUESTRIAN("Конный спорт"),
    MOTORSPORT("Автоспорт"),
    MOTORCYCLING("Мотоспорт"),
    CHESS("Шахматы"),
    CHECKERS("Шашки"),
    SKATEBOARDING("Скейтбординг"),
    PARKOUR("Паркур"),
    POLO("Поло"),
    DIVING("Прыжки в воду"),
    WATER_POLO("Водное поло"),
    SYNCHRONIZED_SWIMMING("Синхронное плавание"),
    MARATHON("Марафон"),
    WALKING("Спортивная ходьба"),
    ORIENTEERING("Спортивное ориентирование"),
    BIATHLON("Биатлон"),
    CURLING("Кёрлинг"),
    BOBSLEIGH("Бобслей"),
    YOGA("Йога"),
    PILATES("Пилатес"),
    BODYBUILDING("Бодибилдинг"),
    DARTS("Дартс"),
    BOWLING("Боулинг"),
    POOL("Бильярд"),
    BEACH_VOLLEYBALL("Пляжный волейбол"),
    BEACH_SOCCER("Пляжный футбол"),
    BEACH_TENNIS("Пляжный теннис"),
    BMX("BMX"),
    DRIFTING("Дрифтинг"),
    PAINTBALL("Пейнтбол"),
    LASER_TAG("Лазертаг"),
    AIRSOFT("Страйкбол"),
    TUG_OF_WAR("Перетягивание каната"),
    SUMO("Сумо");

    private final String name;

    Sports(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

