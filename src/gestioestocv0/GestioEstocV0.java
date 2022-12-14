/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestioestocv0;

import java.util.Scanner;

/**
 *
 * @author super
 */
public class GestioEstocV0 {

    private static Scanner lector = new Scanner(System.in);
    //id_categoria, nom
    private static String[][] categories = {{"0", "Fontaneria"}, {"1", "Electricitat"}, {"2", "Pintura"}};
    //id_article, nom, preu, quantitat, id_categoria
    private static String[][] articles = {
        {"0", "aixeta dutxa", "22.45", "4", "0"},
        {"2", "maniga", "12.22", "0", "0"},
        {"4", "endoll doble", "3.45", "8", "1"},
        {"5", "cable toma-terra 5m", "8.79", "0", "1"},
        {"6", "bombeta led 15w", "2.99", "10", "1"},
        {"7", "15 litres blanc mate", "56.44", "4", "2"},
        {"8","5 litres barnis teka","21.3","3","2"}};

    private static String[][] aux;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        int opcio = 0;
        int idArticle, idCategoria;
        int posicio = -1;
        boolean sortir = false;
        String nomCategoria = "";
        boolean trobat = false;
        boolean existeix = false;
        int unitats = 0;
        double subtotal = 0, total = 0, totalGeneral=0;

        do {
            System.out.println("Selecciona una opció: ");
            System.out.println("1. Gestió d'Articles");
            System.out.println("2. Gestió d'Informes");
            System.out.println("0. Sortir");
            System.out.println("");
            opcio = lector.nextInt();
            lector.nextLine();

            //AMPILACIÓ:
            //PODER GESTIONAR CATEGORIES
            switch (opcio) {
                case 1: //Menú principal 1. Gestió d'Articles
                    System.out.println("Selecciona una opció: ");
                    System.out.println("1. Alta nou article"); // id automàtic, no ha d'existir un article amb el mateix nom
                    System.out.println("2. Baixa d'article"); // no ha de tenir estoc per poder eliminar-lo
                    System.out.println("3. Afegir unitats a un article");
                    System.out.println("4. Eliminar unitats d'un article"); // no es poden quedar els articles en negatiu
                    System.out.println("5. Llistar articles");
                    System.out.println("0. Sortir");
                    while (!lector.hasNextInt()) {
                        System.out.println("Selecciona una opció: ");
                        System.out.println("1. Alta nou article"); // id automàtic, no ha d'existir un article amb el mateix nom
                        System.out.println("2. Baixa d'article"); // no ha de tenir estoc per poder eliminar-lo
                        System.out.println("3. Afegir unitats a un article");
                        System.out.println("4. Eliminar unitats d'un article"); // no es poden quedar els articles en negatiu
                        System.out.println("0. Sortir");
                        lector.nextLine();
                    }
                    opcio = lector.nextInt();
                    lector.nextLine();

                    switch (opcio) {
                        case 1: //Submenú G.Articles 1. Alta nou article
                            System.out.println("submenu 1.1");

                            String[] article = new String[5];

                            if (articles.length == 0) {
                                article[0] = "0";
                            } else {
                                article[0] = String.valueOf(Integer.parseInt(articles[articles.length - 1][0]) + 1);
                            }

                            System.out.println("Donam el nom de l'article");
                            article[1] = lector.nextLine();
                            boolean existeixArticle = false;
                            do {
                                //comprovem si ja existeix un article el nom de l'article
                                for (int i = 0; i < articles.length && !existeixArticle; i++) {
                                    if (articles[i][0].equalsIgnoreCase(article[1])) {
                                        existeixArticle = true;
                                    }
                                }
                                if (existeixArticle) {
                                    System.out.println("El nom d'article ja existeix");
                                    System.out.println("Donam un nou nom d'article");
                                    article[1] = lector.nextLine();
                                }
                            } while (existeixArticle);

                            System.out.println("Donam el preu de l'article");
                            article[2] = lector.nextLine(); //faltaria validar que siga doble
                            System.out.println("Donam la quantitat");
                            article[3] = lector.nextLine(); //faltaria validar que siga int
                            System.out.println("A quina categoria pertany?");

                            boolean existeixCategoria = false;
                            do {
                                //Llistem les categories
                                System.out.printf("%-4s %15s\n", "id", "Categoria");
                                System.out.printf("%20s\n", "--------------------");
                                for (int i = 0; i < categories.length; i++) {
                                    System.out.printf("%-4s %15s\n", categories[i][0], categories[i][1]);
                                }
                                article[4] = lector.nextLine(); //faltaria validar que és int

                                //comprovem que existeix la categoria
                                for (int i = 0; i < categories.length && !existeixCategoria; i++) {
                                    if (categories[i][0].equalsIgnoreCase(article[4])) {
                                        existeixCategoria = true;
                                    }
                                }
                            } while (!existeixCategoria);

                            //creem un nou array amb una posició més
                            aux = new String[articles.length][articles[0].length];

                            //copiem tots els valors
                            for (int i = 0; i < articles.length; i++) {
                                for (int j = 0; j < articles[0].length; j++) {
                                    aux[i][j] = articles[i][j];

                                }
                            }
                            //afegim al final
                            aux[aux.length - 1][0] = article[0];
                            aux[aux.length - 1][1] = article[1];
                            aux[aux.length - 1][2] = article[2];
                            aux[aux.length - 1][3] = article[3];
                            aux[aux.length - 1][4] = article[4];

                            //Intercanviem valors
                            articles = aux;

                            break;

                        case 2: //Submenú G.Articles 2. Baixa d'article

                            posicio = -1;

                            System.out.println("Selecciona un article per donar de baixa...");
                            //Llistem els articles
                            nomCategoria = "";
                            trobat = false;
                            System.out.printf("%4s %25s %8s %10s %6s %15s\n", "id", "Nom", "Preu U.", "Quantitat", "idCat", "Categoria");
                            for (int i = 0; i < articles.length; i++) {
                                for (int j = 0; j < categories.length && !trobat; j++) {
                                    if (categories[i][0].equalsIgnoreCase(articles[i][4])) {
                                        nomCategoria = categories[i][1];
                                        trobat = true;
                                    }
                                }
                                System.out.printf("%4s %25s %8.2f %10s %6s %15s\n",
                                        articles[i][0],
                                        articles[i][1],
                                        Double.parseDouble(articles[i][2]),
                                        articles[i][3],
                                        articles[i][4],
                                        nomCategoria);
                            }
                            System.out.print("id Article: ");
                            while (!lector.hasNextInt()) {
                                System.out.print("id Article: ");
                                lector.nextLine();
                            }
                            idArticle = lector.nextInt();
                            System.out.println("");

                            //comprovem que el idArticle existeix
                            existeix = false;
                            for (int i = 0; i < articles.length && !existeix; i++) {
                                if (Integer.parseInt(articles[i][0]) == idArticle) {
                                    existeix = true;
                                    posicio = i;
                                }

                            }
                            if (existeix) {
                                //creem un nou array amb 1 posició menys
                                aux = new String[articles.length - 1][articles[0].length];

                                //copiem tots els elements
                                for (int i = 0, j = 0; i < articles.length; i++) {
                                    if (i != posicio) {
                                        aux[j][0] = articles[i][0];
                                        aux[j][1] = articles[i][1];
                                        aux[j][2] = articles[i][2];
                                        aux[j][3] = articles[i][3];
                                        aux[j][4] = articles[i][4];
                                        j++;
                                    }
                                }

                                //intercanviem valors
                                articles = aux;
                            } else {
                                System.out.println("No existeix cap article amb id: " + idArticle);
                            }

                            break;
                        case 3: //Submenú G.Articles 3. Afegir unitats a un article

                            //Llistem tots els articles
                            nomCategoria = "";
                            trobat = false;
                            System.out.printf("%4s %25s %8s %10s %6s %15s\n", "id", "Nom", "Preu U.", "Quantitat", "idCat", "Categoria");
                            for (int i = 0; i < articles.length; i++) {
                                for (int j = 0; j < categories.length && !trobat; j++) {
                                    if (categories[i][0].equalsIgnoreCase(articles[i][4])) {
                                        nomCategoria = categories[i][1];
                                        trobat = true;
                                    }
                                }
                                System.out.printf("%4s %25s %8.2f %10s %6s %15s\n",
                                        articles[i][0],
                                        articles[i][1],
                                        Double.parseDouble(articles[i][2]),
                                        articles[i][3],
                                        articles[i][4],
                                        nomCategoria);
                            }
                            System.out.print("id Article: ");
                            while (!lector.hasNextInt()) {
                                System.out.print("id Article: ");
                                lector.nextLine();
                            }
                            idArticle = lector.nextInt();
                            System.out.println("");

                            //comprovem que el idArticle existeix
                            existeix = false;
                            for (int i = 0; i < articles.length && !existeix; i++) {
                                if (Integer.parseInt(articles[i][0]) == idArticle) {
                                    existeix = true;
                                    posicio = i;
                                }
                            }
                            if (existeix) {
                                do {
                                    System.out.print("Indica el número d'unitats a eliminar: ");
                                    while (!lector.hasNextInt()) {
                                        System.out.print("Indica el número d'unitats a eliminar: ");
                                        lector.nextLine();
                                    }
                                    unitats = lector.nextInt();
                                    if (unitats <= 0) {
                                        System.out.println("No pots afegir 0 unitats o unitats negatives...");
                                    }
                                } while (unitats <= 0);

                                //Incrementem les unitats a l'article
                                articles[posicio][3] = String.valueOf(Integer.parseInt(articles[posicio][3]) + unitats);

                                //mostrem l'article
                                System.out.printf("%4s %25s %8s %10s %6s %15s\n", "id", "Nom", "Preu U.", "Quantitat", "idCat", "Categoria");
                                System.out.printf("%4s %25s %8.2f %10s %6s %15s\n",
                                        articles[posicio][0],
                                        articles[posicio][1],
                                        Double.parseDouble(articles[posicio][2]),
                                        articles[posicio][3],
                                        articles[posicio][4],
                                        nomCategoria);
                            } else {
                                System.out.println("No existeix cap article amb id: " + idArticle);
                            }
                            break;
                        case 4: //Submenú G.Articles 4. Eliminar unitats d'un article

                            //Llistem tots els articles
                            nomCategoria = "";
                            trobat = false;
                            System.out.printf("%4s %25s %8s %10s %6s %15s\n", "id", "Nom", "Preu U.", "Quantitat", "idCat", "Categoria");
                            for (int i = 0; i < articles.length; i++) {
                                for (int j = 0; j < categories.length && !trobat; j++) {
                                    if (categories[i][0].equalsIgnoreCase(articles[i][4])) {
                                        nomCategoria = categories[i][1];
                                        trobat = true;
                                    }
                                }
                                System.out.printf("%4s %25s %8.2f %10s %6s %15s\n",
                                        articles[i][0],
                                        articles[i][1],
                                        Double.parseDouble(articles[i][2]),
                                        articles[i][3],
                                        articles[i][4],
                                        nomCategoria);
                            }
                            System.out.print("id Article: ");
                            while (!lector.hasNextInt()) {
                                System.out.print("id Article: ");
                                lector.nextLine();
                            }
                            idArticle = lector.nextInt();
                            System.out.println("");

                            //comprovem que el idArticle existeix
                            existeix = false;
                            for (int i = 0; i < articles.length && !existeix; i++) {
                                if (Integer.parseInt(articles[i][0]) == idArticle) {
                                    existeix = true;
                                    posicio = i;
                                }
                            }
                            if (existeix) {
                                do {
                                    System.out.print("Indica el número d'unitats a afegir: ");
                                    while (!lector.hasNextInt()) {
                                        System.out.print("Indica el número d'unitats a afegir: ");
                                        lector.nextLine();
                                    }
                                    unitats = lector.nextInt();
                                    if (unitats <= 0) {
                                        System.out.println("No pots afegir 0 unitats o unitats negatives...");
                                    }
                                } while (unitats <= 0);

                                //Incrementem les unitats a l'article
                                articles[posicio][3] = String.valueOf(Integer.parseInt(articles[posicio][3]) - unitats);

                                //mostrem l'article
                                System.out.printf("%4s %25s %8s %10s %6s %15s\n", "id", "Nom", "Preu U.", "Quantitat", "idCat", "Categoria");
                                System.out.printf("%4s %25s %8.2f %10s %6s %15s\n",
                                        articles[posicio][0],
                                        articles[posicio][1],
                                        Double.parseDouble(articles[posicio][2]),
                                        articles[posicio][3],
                                        articles[posicio][4],
                                        nomCategoria);
                            } else {
                                System.out.println("No existeix cap article amb id: " + idArticle);
                            }

                            break;
                        case 5: //Submenú G.Articles 5. Llistar articles
                            nomCategoria = "";
                            trobat = false;
                            System.out.printf("%4s %25s %8s %10s %6s %15s\n", "id", "Nom", "Preu U.", "Quantitat", "idCat", "Categoria");
                            for (int i = 0; i < articles.length; i++) {
                                for (int j = 0; j < categories.length && !trobat; j++) {
                                    if (categories[j][0].equalsIgnoreCase(articles[i][4])) {
                                        nomCategoria = categories[j][1];
                                        trobat = true;
                                    }
                                }
                                System.out.printf("%4s %25s %8.2f %10s %6s %15s\n",
                                        articles[i][0],
                                        articles[i][1],
                                        Double.parseDouble(articles[i][2]),
                                        articles[i][3],
                                        articles[i][4],
                                        nomCategoria);
                            }
                            System.out.println("");
                            break;
                        case 0:
                            System.out.println("Tornant al menú principal...");
                            break;
                        default:
                            System.out.println("Opció incorrecta...");
                    }
                    break;
                case 2: //Menú principal 2. Gestió d'Informes
                    System.out.println("1. Obtenir inventari complet"); // valorat amb un total
                    System.out.println("2. Obtenir inventari d'una categoria"); //valorat amb un total
                    System.out.println("3. Obtenir inventari ordenat per categories"); //amb valoració total per categories
                    System.out.println("4. Obtenir tots els articles sense estoc");
                    System.out.println("5. Obtenir articles amb import superior a 10");
                    System.out.println("6. Obtenir articles amb import superior a 10 ordenat per categories");
                    System.out.println("0. Sortir");
                    while (!lector.hasNextInt()) {
                        System.out.println("1. Obtenir inventari complet"); // valorat amb un total
                        System.out.println("2. Obtenir inventari d'una categoria"); //valorat amb un total
                        System.out.println("3. Obtenir inventari ordenat per categories"); //amb valoració total per categories
                        System.out.println("4. Obtenir tots els articles sense estoc");
                        System.out.println("5. Obtenir articles amb import superior a 10");
                        System.out.println("6. Obtenir articles amb import superior a 10 ordenat per categories");
                        System.out.println("0. Sortir");
                        lector.nextLine();
                    }
                    opcio = lector.nextInt();
                    lector.nextLine();

                    switch (opcio) {

                        case 1: //Submenú G.Informes 1. Obtenir inventari complet
                            // valorat amb un total

                            nomCategoria = "";
                            trobat = false;
                            subtotal = 0;
                            total = 0;
                            System.out.printf("%4s %25s %8s %10s %6s %15s %8s\n",
                                    "id",
                                    "Nom",
                                    "Preu U.",
                                    "Quantitat",
                                    "idCat",
                                    "Categoria",
                                    "Subtotal");
                            for (int i = 0; i < articles.length; i++) {
                                trobat = false;
                                for (int j = 0; j < categories.length && !trobat; j++) {
                                    if (categories[j][0].equalsIgnoreCase(articles[i][4])) {
                                        nomCategoria = categories[j][1];
                                        trobat = true;
                                    }
                                }

                                subtotal = Double.parseDouble(articles[i][2]) * Double.parseDouble(articles[i][3]);
                                total = total + subtotal;

                                System.out.printf("%4s %25s %8.2f %10s %6s %15s %8.2f\n",
                                        articles[i][0],//id
                                        articles[i][1],//Nom
                                        Double.parseDouble(articles[i][2]),//Preu U.
                                        articles[i][3],//Quantitat
                                        articles[i][4],//idCat
                                        nomCategoria,//Nom Categoria
                                        subtotal);
                            }
                            System.out.printf("%73s: %7.2f\n", "Total", total);
                            System.out.printf("");

                            break;
                        case 2: //Submenú G.Informes2. Obtenir inventari d'una categoria
                            //valorat amb un total

                            //Llistem les categories
                            System.out.printf("%-4s %15s\n", "id", "Nom");
                            for (int i = 0; i < categories.length; i++) {
                                System.out.printf("%-4s %15s\n", categories[i][0], categories[i][1]);
                            }

                            System.out.print("Selecciona una categoria... ");
                            while (!lector.hasNextInt()) {
                                System.out.print("Selecciona una categoria... ");
                                lector.nextLine();
                            }
                            idCategoria = lector.nextInt();
                            lector.nextLine();

                            //comprovem que el idArticle existeix
                            existeix = false;
                            for (int i = 0; i < categories.length && !existeix; i++) {
                                if (Integer.parseInt(categories[i][0]) == idCategoria) {
                                    existeix = true;
                                    posicio = i;
                                }
                            }
                            if (existeix) {
                                total = 0;
                                subtotal = 0;
                                System.out.println("id: " + idCategoria + " Nom: " + categories[posicio][1]);
                                System.out.printf("%4s %25s %8s %10s %10s\n", "id", "Nom", "Preu U.", "Quantitat", "Subtotal");
                                for (int i = 0; i < articles.length; i++) {
                                    if (Integer.parseInt(articles[i][4]) == idCategoria) {
                                        subtotal = Double.parseDouble(articles[i][2]) * Double.parseDouble(articles[i][3]);
                                        total = total + subtotal;
                                        System.out.printf("%4s %25s %8.2f %10s %10.2f\n",
                                                articles[i][0], //idArticle
                                                articles[i][1], //nom
                                                Double.parseDouble(articles[i][2]), //preu unitari
                                                articles[i][3],//quantitat  
                                                subtotal);
                                    }
                                }
                                System.out.printf("%52s: %7.2f\n", "Total", total);
                            } else {
                                System.out.println("No existeix cap categoria amb id: " + idCategoria);
                            }

                            break;
                        case 3: //Submenú G.Informes7. Obtenir inventari ordenat per categories
                            //amb valoració total per categories i general

                            totalGeneral = 0;
                            for (int i = 0; i < categories.length; i++) {
                                subtotal = 0;
                                total = 0;
                                System.out.println("id: " + categories[i][0] + " Nom: " + categories[i][1]);
                                System.out.printf("%4s %25s %8s %10s %10s\n", "id", "Nom", "Preu U.", "Quantitat", "Subtotal");
                                for (int j = 0; j < articles.length; j++) {
                                    if (categories[i][0].equalsIgnoreCase(articles[j][4])) {
                                        subtotal = Double.parseDouble(articles[j][2]) * Double.parseDouble(articles[j][3]);
                                        total = total + subtotal;
                                        totalGeneral = totalGeneral + total;

                                        System.out.printf("%4s %25s %8.2f %10s %10.2f\n",
                                                articles[j][0], //idArticle
                                                articles[j][1], //nom
                                                Double.parseDouble(articles[j][2]), //preu unitari
                                                articles[j][3],//quantitat 
                                                subtotal);

                                    }
                                }
                                //imprimim total de categoria
                                System.out.printf("%52s: %7.2f\n", "Subtotal", total);
                            }
                            //imprimim total general
                            System.out.println("");
                            System.out.printf("%52s: %7.2f\n", "Total", totalGeneral);

                            break;
                        case 4: //Submenú G.Informes8. Obtenir tots els articles sense estoc                        

                            System.out.println("Articles sense estoc:");
                            System.out.printf("%4s %25s %8s %10s %6s %15s\n", "id", "Nom", "Preu U.", "Quantitat", "idCat", "Categoria");

                            for (int i = 0; i < articles.length; i++) {
                                if (articles[i][3].equalsIgnoreCase("0")) {
                                    idCategoria = Integer.parseInt(articles[i][4]);
                                    //busquem la posició de la categoria per traure el nom
                                    trobat = false;
                                    for (int j = 0; j < categories.length && !trobat; j++) {
                                        if (Integer.parseInt(categories[j][0]) == idCategoria) {
                                            trobat = true;
                                            posicio = j;
                                        }
                                    }

                                    System.out.printf("%4s %25s %8.2f %10s %6s %15s\n",
                                            articles[i][0],//id
                                            articles[i][1],//Nom
                                            Double.parseDouble(articles[i][2]),//Preu U.
                                            articles[i][3],//Quantitat
                                            articles[i][4],//idCat
                                            categories[posicio][1]);//Nom Categoria                   
                                }
                            }

                            break;
                        case 5: //Submenú G.Informes9. Obtenir articles amb import superior a 10

                            for (int i = 0; i < articles.length; i++) {

                                if (Double.parseDouble(articles[i][2]) > 10) { //import >10
                                    idCategoria = Integer.parseInt(articles[i][4]);
                                    //busquem la posició de la categoria per traure el nom
                                    trobat = false;
                                    for (int j = 0; j < categories.length && !trobat; j++) {
                                        if (Integer.parseInt(categories[j][0]) == idCategoria) {
                                            trobat = true;
                                            posicio = j;
                                        }
                                    }

                                    System.out.printf("%4s %25s %8.2f %10s %6s %15s\n",
                                            articles[i][0],//id
                                            articles[i][1],//Nom
                                            Double.parseDouble(articles[i][2]),//Preu U.
                                            articles[i][3],//Quantitat
                                            articles[i][4],//idCat
                                            categories[posicio][1]);//Nom Categoria    
                                }
                            }

                            break;
                        case 6: //Submenú G.Informes10. Obtenir articles amb import superior a 10 ordenat per categories

                            System.out.println("Articles amb import superior a 10 ordenat per categories:");

                            for (int i = 0; i < categories.length; i++) {
                                System.out.println("Categoria: " + categories[i][1]);
                                System.out.printf("%4s %25s %8s %10s %6s %15s\n", "id", "Nom", "Preu U.", "Quantitat", "idCat", "Categoria");

                                for (int j = 0; j < articles.length; j++) {
                                    if (categories[i][0].equalsIgnoreCase(articles[j][4])) {
                                        if (Double.parseDouble(articles[j][2]) > 10) {
                                            System.out.printf("%4s %25s %8.2f %10s %6s %15s\n",
                                                    articles[j][0],//id
                                                    articles[j][1],//Nom
                                                    Double.parseDouble(articles[j][2]),//Preu U.
                                                    articles[j][3],//Quantitat
                                                    articles[j][4],//idCat
                                                    categories[i][1]);//Nom Categoria 
                                        }
                                    }
                                }
                                System.out.println("");
                            }

                            break;
                        case 0:
                            System.out.println("Tornant al menú principal...");
                            break;
                        default:
                            System.out.println("Opció incorrecta...");
                    }
                    break;
                case 0: //Menú principal 0. Sortir
                    System.out.println("Adéu...");
                    sortir = true;
                    break;
                default:
                    System.out.println("Opció incorrecta...");
            }

        } while (!sortir);
    }
}
