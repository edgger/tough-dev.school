За основу взял нарисованную структуру из домашки.

## Необходимо:
1. Вынести интеллектуальный матчинг сотрудников и клиентов
2. Разделить расчеты для клиентов и сотрудников
3. Убрать энтити-сервис воркеров

## Расчет Instability
Instability=Efferent Coupling/(Efferent Coupling+Afferent Coupling)

1. Интеллектуальный матчинг: Instability=0/(0+1)=0
2. Расчеты клиентов: Instability=0/(0+1)=0
3. Расчеты сотрудников: Instability=0/(0+1)=0 
4. Планирование визитов + контроль качества работы: Instability=2/(2+5)=0.29


## План работ
### Нет опыта
1. Вынести интеллектуальный матчинг с помощью паттерна Strangler Fig Application
2. Разделить расчеты для клиентов и сотрудников с помощью паттерна Tactical Forking
3. Убрать энтити-сервис воркеров

### Нет ресурсов
1. Убрать энтити-сервис воркеров
2. Разделить расчеты для клиентов и сотрудников с помощью паттерна Tactical Forking
3. Вынести интеллектуальный матчинг с помощью паттерна Strangler Fig Application