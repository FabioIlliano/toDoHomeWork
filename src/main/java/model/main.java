package Model;

public class main {
    public static void main(String[] args) {
        Utente Admin = new Utente("Admin", "1234");

        if (!Admin.Login())
        {
            System.out.println("Credenziali errate!");
            return;
        }
        Bacheca NuovaBacheca = Admin.CreaBacheca();
        System.out.println(NuovaBacheca.toString());

        /*ToDO Cucina = new ToDO("Pasta e patate", "Devo cucinare pasta e patate", "www.pastepatan.potatos", false, 11, 12, "Giallo");
        System.out.println(Cucina.toString());

        //modifica stato
        Cucina.ModificaStato();

        System.out.println(Cucina.Stato);*/

    }


}
