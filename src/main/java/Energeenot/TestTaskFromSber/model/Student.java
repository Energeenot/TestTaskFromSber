package Energeenot.TestTaskFromSber.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Сущность, представляющая студента.
 * <p>
 *     Этот класс используется для хранения данных о студенте, включая его фамилию, имя,
 *     отчество, возраст и средний балл. Данные хранятся в таблице {@code student} базы данных.
 * </p>
 */

@Entity()
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    /**
     * Уникальный идентификатор студента.
     * <p>
     *     Генерируется автоматически в бд с помощью стратегии
     *     {@link GenerationType#IDENTITY}.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Обязательное поле: фамилия
     * Ограничение проверяется на уровне базы данных:
     * <code> not null</code>
     */
    @Column(name = "surname")
    private String surname;

    /**
     * Обязательное поле: имя
     * Ограничение проверяется на уровне базы данных:
     * <code> not null</code>
     */
    @Column(name = "name")
    private String name;

    /**
     * Необязательное поле: отчество
     * Ограничение проверяется на уровне базы данных:
     */
    @Column(name = "patronymic")
    private String patronymic;

    /**
     * Обязательное поле: возраст
     * Ограничение проверяется на уровне базы данных:
     * <code> not null</code>
     */
    @Column(name = "age")
    private int age;

    /**
     * Обязательное поле: средняя оценка
     * Ограничение проверяется на уровне базы данных:
     * <code>CHECK (average_mark >= 1.00 AND average_mark <= 5.00)</code>
     */
    @Column(name = "average_mark")
    private Double averageMark;

}
