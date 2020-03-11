package com.company;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ListOfCars {

    public static void main(String[] args) {
        Car[] tablica = new Car[]{
                new Car("Renault", "Captur", 2017, 1100, 45600, 12000),
                new Car("Renault", "Kadjar", 2018, 1499, 84500, 7800),
                new Car("Ford", "Focus Plus", 2011, 1560, 21400, 57400),
                new Car("Honda", "CRV", 2017, 1670, 63400, 13500),
                new Car("Honda", "Jazz", 2009, 1199, 15400, 82000),
                new Car("Audi", "A6", 2015, 1850, 98000, 23400),
                new Car("Opel", "Astra", 2017, 1300, 56000, 5700),
                new Car("Opel", "Mokka", 2019, 1400, 73000, 2300),
                new Car("Opel", "Corsa", 2012, 1100, 21000, 79320),
                new Car("Citroen", "C4", 2015, 1780, 41900, 23400),
                new Car("Hyundai", "Santa Cruz", 2018, 1820, 97500, 11800),
                new Car("Hyundai", "ix35", 2014, 1700, 34560, 41600),
                new Car("Citroen", "C2", 2014, 1100, 25700, 40500),
                new Car("Mitsubishi", "ASX", 2017, 1450, 47500, 14600),
                new Car("Nissan", "Quashqai", 2017, 1800, 76000, 17800),
                new Car("Renault", "Clio", 2010, 1100, 12300, 87000),
                new Car("Fiat", "Panda", 2011, 1200, 13500, 105000),
                new Car("Ford", "Fiesta", 2015, 1100, 29000, 24700),
                new Car("Nissan", "Leaf", 2017, 1200, 74000, 11300),
                new Car("Volvo", "S90", 2015, 1900, 46700, 34700),
                new Car("Volkswagen", "Golf", 2016, 1560, 39500, 24730)
        };
        List<Car> samochody = Arrays.asList(tablica);

        // samochody wyprodukowane po 2014 roku posortowane wg ceny
        List<Car> lista = samochody
                .stream()
                .filter(p -> p.getRokProdukcji() > 2014)
                .sorted((p1, p2) -> (int) (p1.getCena() - p2.getCena()))
                .collect(Collectors.toList());
        for (Car s : lista)
            System.out.println(s.getMarka() + " " + s.getModel() + " " + s.getCena());

        // samochody pogrupowane wg marek a w ramach marki posortowane wg modelu
        Map<String, List<Car>> pogrupowane = lista
                .stream()
                .sorted((p1,p2) -> (p1.getModel().compareTo(p2.getModel())))
                .collect(Collectors.groupingBy(Car::getMarka));
        for (Map.Entry<String, List<Car>> entry : pogrupowane.entrySet()) {
            System.out.println("============================");
            System.out.println("Marka: " + entry.getKey());
            for (Car sam : entry.getValue())
                System.out.println(sam.toString());
        }

        //region obliczenie średniego wieku pojazdów
        Double średniWiek = lista
                .stream()
                .mapToInt(p -> LocalDate.now().getYear() - p.getRokProdukcji())
                .average().getAsDouble();
        System.out.format("Sredni wiek pojazdu to %f.1", średniWiek);


        // średnia cena samochodu o pojemności z przedziału (1500,2000) i roku produkcji > 2010
        Predicate<Car> filtrPojemności = (p) -> (p.getPojemnośćSilnika() > 1500 && p.getPojemnośćSilnika() < 2000);
        Predicate<Car> filtrRokuProdukcji = (p) -> (p.getRokProdukcji() > 2010);

        double średniaCena = lista
                .stream()
                .filter(filtrPojemności)
                .filter(filtrRokuProdukcji)
                .mapToInt(p-> (int)p.getCena())
                .average().getAsDouble();
        System.out.format("Srednia cena wynosi %.2f %n", średniaCena);

        // wydobycie samych cen samochodów
        List<Double> ceny = lista
                .stream()
                .map(p -> p.getCena())
                .collect(Collectors.toList());
        System.out.println("==== CENY SAMOCHODÓW ====");
        System.out.println(ceny);

        // wydobycie pary: marka + PRZEBIEG ROCZNY
        System.out.println("==== SAMOCHODY POSORTOWANE WG PRZEBIEGU ROCZNEGO ====");
        List<Pair<String, Double>> pary = lista
                .stream()
                .sorted((p1, p2) ->
                        ((int) (p1.getPrzebieg() / (LocalDate.now().getYear() - p1.getRokProdukcji() + 1) - p2.getPrzebieg() / (LocalDate.now().getYear() - p2.getRokProdukcji() + 1))))
                .map(p -> p.getPara())
                .collect(Collectors.toList());
        for (Pair<String, Double> para : pary)
            System.out.println(para.getElement1() + " => " + para.getElement2());

        // lista unikalnych nazw marek samochodów z wykorzystaniem własnego Collectora
        Collector<String, StringJoiner, String> myCollector = Collector.of(
                () -> new StringJoiner(" : "),          // supplier
                (j, p) -> j.add(p.toUpperCase()),              // accumulator
                (j1, j2) -> j1.merge(j2),                       // combiner
                StringJoiner::toString);                        // finisher

        String marki= lista
                .stream()
                .map(p -> p.getMarka())
                .sorted()   // sortowanie na typie String nie wymaga parametrów (lambdy)
                .distinct() // unikalne marki
                .collect(myCollector);
        System.out.println(marki);

        // przykład strumienia z operacją println w pętli for (wylistowanie modeli samochodów)
        System.out.println("==== Lista modeli samochodów ==== ");
        lista .stream()
                .map(p -> p.getModel())
                .forEach(System.out::print);

        // ulepszona wersja z interfejsem funkcyjnym Consumer
        System.out.println("==== Wykorzystanie interfejsu funkcyjnego Consumer ==== ");
        Consumer<String> showModels = t -> System.out.print(t + ":");
        lista .stream()
                .map(p -> p.getModel())
                .forEach(showModels);

    }
}