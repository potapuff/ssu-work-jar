/*Фигня для того чтобы при завершении любого шага 
компоненты, которые реализуют даный интерфейс смогли изменить свою
модель (значение для проверки на правильность)*/

package edu.sumdu.dl.common;

public interface Updateable {

    public void updateValuesToCheck();
}
