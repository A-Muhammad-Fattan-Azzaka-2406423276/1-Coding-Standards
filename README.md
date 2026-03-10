# Refleksi - Modul 1

### 1. Clean Code 
Saya telah menerapkan prinsip kode yang bersih dengan cara:
Penamaan Jelas: Memberikan nama variabel dan fungsi yang mudah dimengerti fungsinya tanpa perlu banyak komentar.
Pembagian Tugas: Memisahkan kode sesuai tugasnya. Controller untuk mengatur alur web, Service untuk logika bisnis, dan Repository untuk menyimpan data.

### 2. Secure Coding
ID Unik (UUID): Saya menggunakan UUID untuk ID produk, bukan sekadar angka urut yang di increment. 
Ini membuat ID produk sulit ditebak oleh pihak luar, sehingga data lebih aman dan tiap produk memiliki unique id

# Refleksi - Modul 2

### 1. Perbaikan Kualitas Kode (Code Quality Issues)
- Security Hotspot (Dependency Verification): SonarCloud mendeteksi absennya file verification-metadata.xml. 
  Strategi saya adalah melakukan tinjauan manual dan menandainya sebagai Safe karena semua dependensi yang digunakan berasal dari sumber resmi yang terpercaya
- Unit Test (assertThrows)**: Terdapat code smell di mana blok lambda assertThrows berisi lebih dari satu pernyataan. 
  Saya memisahkan pemanggilan method pendukung ke luar blok agar pengujian hanya fokus pada satu baris kode yang memicu exception
- Field Injection vs Constructor Injection: Saya menghapus penggunaan @Autowired langsung pada field variabel karena mempersulit unit testing. 
  Sebagai gantinya, saya menerapkan Constructor Injection secara manual di ProductServiceImpl.java
- Laporan Coverage (JaCoCo): Awalnya coverage terbaca 0% karena SonarCloud memerlukan format XML. 
  Saya memperbarui `build.gradle.kts` untuk mengaktifkan laporan XML JaCoCo dan menentukan jalurnya agar data cakupan tes muncul di dashboard

### 2. Analisis Implementasi CI/CD
Proses Continuous Integration (CI) sudah sepenuhnya terpenuhi karena setiap perubahan kode di GitHub secara otomatis memicu workflow, pengujian otomatis, dan analisis kualitas kode melalui SonarCloud. 
Namun, Continuous Deployment (CD) belum tercapai secara sempurna. Meskipun Dockerfile telah dibuat untuk membungkus aplikasi, proses auto-deploy ke platform seperti Render atau Koyeb masih terhambat kendala teknis kartu kredit pada layanan tersebut. 

# Refleksi - Modul 3
### 1. Explain what principles you apply to your project!
Single Responsibility Principle (SRP): Memisahkan CarController dari ProductController.java ke filenya sendiri. Masing-masing controller kini memiliki 
satu alasan untuk berubah, yaitu hanya untuk mengatur routing entitas mereka sendiri.Liskov Substitution Principle (LSP): Menghilangkan extends ProductController pada deklarasi CarController. Sebuah CarController tidak seharusnya menggantikan fungsi dari ProductController.Dependency Inversion Principle (DIP): Mengubah injeksi tipe konkrit CarServiceImpl pada CarController menjadi interface CarService. Hal ini memastikan controller bergantung pada abstraksi, bukan detail implementasi.
### 2. Explain the advantages of applying SOLID principles to your project with examples.
Kode menjadi lebih modular, mudah dibaca, dan maintainable. Sebagai contoh, dengan memisahkan CarController ke file baru (SRP), saya bisa dengan mudah mencari, membaca, dan melakukan testing pada routing mobil tanpa tercampur dengan logika produk. Jika di masa depan ada bug terkait rute "Car", saya tahu persis di mana harus memperbaikinya tanpa risiko merusak rute "Product". Begitu juga penerapan DIP yang membuat penggantian business logic dari CarService jauh lebih aman, asal kontrak pada interface-nya tetap dipatuhi.
### 3. Explain the disadvantages of not applying SOLID principles to your project with examples.
Tanpa SOLID, kode akan menjadi sangat tightly-coupled (saling bergantung satu sama lain secara kaku) dan rentan rusak ketika diubah (fragile). Contohnya: Jika CarController dibiarkan berada dalam satu file yang sama dengan ProductController dan terus meng-extend-nya, jika suatu saat kita memodifikasi method dasar pada ProductController atau menambahkan validasi global, CarController bisa tiba-tiba error atau bertingkah aneh karena ikut mewarisi perubahan tersebut, padahal konteks datanya berbeda. Semakin lama dikembangkan, ukuran kelasnya akan membengkak, sangat sulit dibaca, dan testing-nya akan jauh lebih rumit.

# Refleksi - Modul 4
### 1. Mengikuti alur TDD dalam tutorial ini sangat berguna karena memaksa saya untuk memikirkan perilaku yang diharapkan sebelum menulis implementasi apapun. 
Berdasarkan pertanyaan reflektif dari Percival, pengujian membantu saya menangkap kasus-kasus tepi lebih awal seperti status yang tidak valid dan produk yang kosong, sehingga meningkatkan kebenaran program. Alur kerja ini juga membuat refactoring lebih aman karena saya dapat memverifikasi bahwa perilaku yang sudah ada tidak rusak setelah memperkenalkan OrderStatus enum. Namun, saya menyadari masih perlu meningkatkan penulisan integration test agar dapat memastikan semua komponen bekerja bersama dengan benar.
### 2. Unit test yang saya buat dalam tutorial sebagian besar sudah mengikuti prinsip F.I.R.S.T. 
Test bersifat Fast karena menggunakan mock sehingga tidak perlu mengakses database sungguhan. Test bersifat Independent karena setiap test memiliki setUp sendiri dan tidak bergantung pada test lain. Test bersifat Repeatable karena dependensi yang di-mock selalu mengembalikan hasil yang konsisten. Test bersifat Self-validating karena semua test menggunakan assertion bukan print statement. Test bersifat Timely karena ditulis sebelum implementasi sesuai aturan TDD. Hal yang perlu saya tingkatkan adalah memastikan setiap test benar-benar hanya menguji satu hal saja, karena beberapa test masih bisa dipecah lebih lanjut untuk isolasi yang lebih baik.