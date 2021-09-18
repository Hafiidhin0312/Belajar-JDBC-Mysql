import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.Scanner;


public class JDBC {
    public static void main(String[] args) throws SQLException {


        Koneksi konek = new Koneksi("root","","jdbc:mysql://localhost:3306/tokobuahdb");


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException err){
            err.printStackTrace();
        }

        try {
            Connection con = DriverManager.getConnection(konek.url, konek.username, konek.password);
            Statement statement = con.createStatement();
            String query;
            ResultSet result = null;
            PreparedStatement preparedStmt;

            while (true){
                System.out.println("====TOKO BUAH=====");

                System.out.println("1. Daftar Buah");
                System.out.println("2. List Harga buah/kg");
                System.out.println("3. Add Daftar Buah");
                System.out.println("4. Update Daftar Buah");
                System.out.println("5. Delete Daftar Buah");
                System.out.println("6. Pembelian");

                System.out.print(" Pilih Menu : ");
                int pilihmenu = inputint();

                switch (pilihmenu) {
                    case 1:
                        boolean case1 = true;
                        while (case1) {
                            query = "SELECT * FROM tbldaftarbuah";
                            showBuah(query, result, statement);
                            System.out.print("Jika ingin kembali ke menu Utama ketik 'y' jika ingin tetap pada Daftar Buah ketik 'n' ? (y/n) :");
                            if (inputstr().equalsIgnoreCase("y")) {
                               case1 = false;
                            }
                        }
                        break;


                    case 2:
                        boolean case2 = true;
                        while (case2) {
                            query = "SELECT * FROM tbldaftarbuah";
                            showHargaBuah(query, result, statement);

                            System.out.print("Jika ingin kembali ke menu Utama ketik 'y' jika ingin tetap pada Daftar Harga Buah ketik 'n' ? (y/n) :");
                            if (inputstr().equalsIgnoreCase("y")) {
                                case2=false;
                            }
                        }
                        break;
//
//
                    case 3:
                        boolean case3 = true;
                        while (case3) {
                            query = "SELECT * FROM tbldaftarbuah";
                            showBuah(query, result, statement);

                            Scanner input = new Scanner(System.in);
                            System.out.print("Buah : ");
                            String tmbahbuah = input.nextLine();
                            System.out.print("Harga : ");
                            int tmbahharga = input.nextInt();


                            query = " insert into tbldaftarbuah(Nama, Harga)"
                                    + " values (?, ?)";

                            preparedStmt = con.prepareStatement(query);
                            preparedStmt.setString(1, tmbahbuah);
                            preparedStmt.setInt(2, tmbahharga);
                            preparedStmt.execute();


                            query = "SELECT * FROM tbldaftarbuah";
                            showHargaBuah(query, result, statement);

                            System.out.print("Jika ingin kembali ke Menu Utama ketik 'y' Jika ingin tetap menambah buah ketik 'n' ? (y/n) :");
                            if (inputstr().equalsIgnoreCase("y")) {
                               case3 = false;
                            }
                        }
                        break;

                    case 4:
                        Boolean case4 = true;
                        while (case4) {
                            query = "SELECT * FROM tbldaftarbuah";
                            showHargaBuah(query, result, statement);

                            while (true) {
                                System.out.print("Buah apa yang ingin diganti ? Masukkan Kode Buah  : ");
                                int inputan = inputint();

                                query = "SELECT * FROM tbldaftarbuah WHERE Kode = ? ";
                                preparedStmt = con.prepareStatement(query);
                                preparedStmt.setInt(1, inputan);
                                result = preparedStmt.executeQuery();

                                if(!showHargaBuahID(query, result, statement)){

                                    System.out.println("ingin Diganti ? (y/n)");

                                    if (inputstr().equalsIgnoreCase("y")) {
                                        System.out.print("Nama Buah Baru : ");
                                        String nama = inputstr();
                                        System.out.print("Harga : ");
                                        int harga = inputint();

                                        query = "UPDATE tbldaftarbuah  SET Nama = ?, Harga = ? WHERE Kode = ?";

                                        preparedStmt = con.prepareStatement(query);
                                        preparedStmt.setString(1, nama);
                                        preparedStmt.setInt(2, harga);
                                        preparedStmt.setInt(3, inputan);
                                        preparedStmt.execute();


                                        query = "SELECT * FROM tbldaftarbuah";
                                        showHargaBuah(query, result, statement);

                                        System.out.println("Data berhasil DiUpdate");

                                        break;

                                    } else {

                                        System.out.print("Kembali ke menu Utama ? (y/n): ");
                                        if (inputstr().equalsIgnoreCase("y")) {
                                            break;
                                        } else {
                                        }
                                    }
                                }else{
                                    System.out.println("Data tidak ditemukan");
                                }
                            }

                            System.out.print("ingin kembali ke menu Utama ketik 'y' jika ingin  merubah buah yang lain ketik 'n' ? (y/n): ");
                            if (inputstr().equalsIgnoreCase("y")) {
                               case4=false;
                               break;
                            }else{
                                case4 = true;
                            }
                        }
                        break;
//
                case 5:
                    Boolean case5 = true;
                    while (case5) {

                        query = "SELECT * FROM tbldaftarbuah";
                        showHargaBuah(query, result, statement);

                        boolean flag=true;

                        if(flag){
                        System.out.print("Hapus Buah Apa ? Masukkan Kode: ");
                        int inputan2 = inputint();

                            query = "SELECT * FROM tbldaftarbuah WHERE Kode = ? ";
                            preparedStmt = con.prepareStatement(query);
                            preparedStmt.setInt(1, inputan2);
                            result = preparedStmt.executeQuery();

                            if(!showHapusBuahID(query, result, statement)){
                                System.out.print("Apakah Anda Yakin (y/n) ? :  ");

                                if (inputstr().equalsIgnoreCase("y")) {

                                    query = "DELETE FROM tbldaftarbuah  WHERE Kode = ?";

                                    preparedStmt = con.prepareStatement(query);
                                    preparedStmt.setInt(1, inputan2);
                                    preparedStmt.execute();

                                    System.out.println("Data berhasil Dihapus");

                                    flag = false;

                                }else{

                                    flag = true;

                                }
                            }else{
                                System.out.println("Data tidak ditemukan");
                            }
                            }


                        System.out.print("Jika ingin kembali ke menu utama ketik 'y' jika ingin menghapus buah lagi ketik 'n'  (y/n) :");
                        if (inputstr().equalsIgnoreCase("y")) {
                            case5 = false;
                        }
                    }
                    break;

                case 6:
                    boolean case6 = true;
                    while (case6) {
                        query = "SELECT * FROM tbldaftarbuah";
                        showHargaBuah(query, result, statement);

                        while (true) {

                            System.out.print("Beli Buah Yang Mana ? Masukkan Kode : ");
                            int inputan3 = inputint();
                            System.out.print("Berapa Banyak (Kg) ? : ");
                            int inputan4 = inputint();
                            query = "SELECT * FROM tbldaftarbuah WHERE Kode = ?";

                            preparedStmt = con.prepareStatement(query);
                            preparedStmt.setInt(1, inputan3);

                            result = preparedStmt.executeQuery();


                            while (result.next()) {
                                int kodebeli = result.getInt("Kode");
                                String namabuah = result.getString("Nama");
                                int hargabuah = result.getInt("Harga");
                                int total = hargabuah*inputan4;

                                System.out.println(
                                        String.join(" | ", Integer.toString(kodebeli), namabuah, Integer.toString(total))
                                );

                                query = " insert into keranjang(Kode,Nama, Harga, Jumlah)"
                                        + " values (?,?, ?, ?)";

                                preparedStmt = con.prepareStatement(query);
                                preparedStmt.setInt(1, kodebeli);
                                preparedStmt.setString(2, namabuah);
                                preparedStmt.setInt(3, hargabuah);
                                preparedStmt.setInt(4, inputan4);
                                preparedStmt.execute();
                                System.out.println("Data Masuk");

                            }



                            System.out.print("ingin beli lagi ketik 'y' lanjut pembayaran ketik 'n' - (y/n) : ");

                            if (inputstr().equalsIgnoreCase("y")) {
                                continue;
                            } else {
                                break;
                            }

                        }

                        query = "SELECT * FROM keranjang";
                        showkeranjang(query, result, statement);

                        query = "SELECT Harga*Jumlah as Total FROM keranjang";
                        int total = total(query, result, statement);

                        while (true) {

                            System.out.println("=====BAYAR========");
                            System.out.println("masukkan uang");
                            int uang = inputint();

                            if (uang >= total) {
                                int kembali = uang - total;
                                System.out.println("kembalian = Rp. " + kembali);
                                System.out.println("====Terimakasih Sudah Berbelanja====");
                                query = "DELETE FROM keranjang";
                                statement.executeUpdate(query);
                                break;
                            } else {
                                System.out.println("uang anda kurang");
                            }
                        }

                        System.out.print("Jika ingin kembali ke menu utama ketik 'y' jika ingin membeli buah lagi ketik 'n' ? (y/n) :");
                        if (inputstr().equalsIgnoreCase("y")) {
                            case6 = false;
                        }
                    }
                    break;

                    default:
                        System.out.println("diluar pilihan");

                        System.out.print("Ingin keluar aplikasi ketik 'y' atau ingin kembali ke menu Utama ketik 'n' ? (y/n)");
                        if (inputstr().equalsIgnoreCase("y")){
                            System.exit(0);
                            result.close();
                            statement.close();
                            con.close();
                        }else{

                        }


                }

            }



        }catch (SQLException err){
            err.printStackTrace();
        }

    }

    static int inputint(){
        Scanner input = new Scanner(System.in);
        int inputint = input.nextInt();
        return inputint;
    }

    static String inputstr(){
        Scanner input = new Scanner(System.in);
        String inputstr = input.nextLine();
        return inputstr;
    }

    static void showBuah(String query, ResultSet result,Statement statement) throws SQLException {
        result = statement.executeQuery(query);

        while (result.next()){
            String kode = result.getString("Kode");
            String nama = result.getString("Nama");

            System.out.println(
                    String.join(" | ",kode,nama)
            );
        }

    }

    static void showHargaBuah(String query, ResultSet result,Statement statement) throws SQLException {
        result = statement.executeQuery(query);

        int i=0;

        System.out.println("|No| Kode | Nama | Harga | ");

        while (result.next()){
            i+=1;
            String kode = result.getString("Kode");
            String nama = result.getString("Nama");
            int harga = result.getInt("Harga");

            System.out.println(
                    String.join(" |   ",Integer.toString(i),kode,nama,Integer.toString(harga))
            );
        }

    }

    static boolean showHargaBuahID(String query, ResultSet result,Statement statement) throws SQLException {
        int i=0;
        boolean tdkada = true;
        while (result.next()){
            String kode = result.getString("Kode");
            String nama = result.getString("Nama");
            int harga = result.getInt("Harga");
            System.out.println("Anda akan mengganti data buah "+ nama+" dengan harga "+ harga);
            tdkada = false;
        }

        return tdkada;

    }

    static boolean showHapusBuahID(String query, ResultSet result,Statement statement) throws SQLException {
        int i=0;
        boolean tdkada = true;

        while (result.next()){

            String kode = result.getString("Kode");
            String nama = result.getString("Nama");
            int harga = result.getInt("Harga");

            System.out.println("Anda akan menghapus data buah "+ nama+" dengan harga "+ harga);
            tdkada = false;
        }
        return tdkada;
    }

    static void showkeranjang(String query, ResultSet result,Statement statement) throws SQLException {
        result = statement.executeQuery(query);

        System.out.println("===Daftar Belanja Anda=====");

        System.out.println("Kode | Nama | Harga | Jumlah | Total");


        while (result.next()){
            String kode = result.getString("Kode");
            String nama = result.getString("Nama");
            int harga = result.getInt("Harga");
            int jumlah = result.getInt("Jumlah");
            int total = harga*jumlah;

            System.out.println(
                    String.join("  ",kode,nama,Integer.toString(harga),Integer.toString(jumlah),Integer.toString(total))
            );

        }

    }

    static int total(String query, ResultSet result,Statement statement) throws SQLException {
        result = statement.executeQuery(query);
        int total = 0;

        System.out.println("===Total Harga Belanja Anda=====");
        while (result.next()) {
            int hasil = result.getInt("Total");
            total+=hasil;
        }

        System.out.println("RP. "+ total);

        return total;

    }




}
