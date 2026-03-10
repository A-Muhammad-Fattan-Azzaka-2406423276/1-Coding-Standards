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