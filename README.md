# SIPATIK - Sistem Informasi Pencatatan Infak

## 📋 Deskripsi Aplikasi

SIPATIK adalah sistem manajemen keuangan berbasis web yang dirancang khusus untuk mengelola infak dan pengeluaran program beasiswan Pemberdayaan Umat Berkelanjutan (PUB). Sistem ini memungkinkan alumni untuk melakukan konfirmasi infak secara online dan admin untuk mengelola laporan keuangan secara efisien.

### ✨ Fitur Utama

- **Autentikasi Multi-Role**: Sistem login terpisah untuk Admin dan User (Alumni)
- **Manajemen Alumni**: CRUD data alumni dengan filter berdasarkan angkatan
- **Setor Infak Online**: Alumni dapat melakukan konfirmasi setor infak melalui berbagai bank
- **Dashboard Admin**: Monitoring real-time total infak, pengeluaran, dan laporan keuangan
- **Laporan Keuangan**: Sistem laporan kas bulanan dengan detail pemasukan dan pengeluaran
- **Konfirmasi Infak**: Admin dapat menyetujui/tolak transaksi infak
- **Email Notification**: Sistem notifikasi via email untuk verifikasi token

## 🛠️ Teknologi yang Digunakan

### Backend
- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Security** - Autentikasi dan otorisasi
- **Spring Data JPA** - ORM untuk database
- **JWT (JSON Web Token)** - Token-based authentication
- **MySQL 8.0** - Database utama
- **Maven** - Build tool dan dependency management

### Frontend
- **Thymeleaf** - Template engine
- **HTML5/CSS3** - UI components
- **JavaScript** - Interaktivitas dan AJAX
- **Bootstrap** - CSS framework (dalam beberapa halaman error)
- **Font Awesome** - Icons

### Tools & Libraries
- **Lombok** - Code generation
- **Apache POI** - Excel processing
- **OpenPDF** - PDF generation
- **JavaMail** - Email sending
- **HikariCP** - Connection pooling

## 🚀 Cara Menjalankan Proyek

### Default Admin Account
- **Email**: ajengazzahra04@gmail.com
- **Password**: Eks123

## 📁 Struktur Proyek

```
sipatik/
├── src/main/java/com/projek/sipatik/
│   ├── config/           # Konfigurasi aplikasi
│   ├── controllers/      # Controller classes
│   ├── dto/             # Data Transfer Objects
│   ├── models/          # Entity models
│   ├── repositories/    # Data repositories
│   ├── security/        # JWT dan security config
│   └── services/        # Business logic
├── src/main/resources/
│   ├── static/          # CSS, JS, images
│   ├── templates/       # Thymeleaf templates
│   └── application.properties
├── src/test/            # Unit tests
└── pom.xml             # Maven configuration
```

## Cara Menjalankan Projek
1. pertama kali buka http://localhost:8085/auth/

## 🔐 Sistem Autentikasi

### Admin Authentication Flow:
1. Login dengan email/password
2. Sistem kirim token ke email
3. Input token untuk verifikasi
4. JWT token disimpan di cookie
5. Redirect ke dashboard admin

### User Authentication Flow:
1. Login dengan email/password
2. JWT token langsung dibuat
3. Redirect ke dashboard user

## 📊 Fitur Admin

- **Dashboard**: Overview total infak, pengeluaran, kas awal, dan kas akhir
- **Manajemen Alumni**: Tambah, edit, hapus, lihat data alumni
- **Daftar Infak**: Konfirmasi/tolak transaksi infak, tambah infak (apabila alumni lupa konfirmasi infaq nya ke website), edit infak, hapus infak
- **Laporan Kas**: Laporan keuangan bulanan detail, tambah kas, edit kas, filter laporan kas berdasarkan bulan dan tahun
- **Pengeluaran**: Input dan manage pengeluaran

## 👥 Fitur User (Alumni)

- **Dashboard**: Riwayat setor infak
- **Setor Infak**: Form setor infak online
- **Rekap Infak**: Laporan setor infak pribadi
- **Profil**: Update data pribadi

## 🔧 Konfigurasi Environment

### Development
```properties
server.port=8086
spring.profiles.active=development
logging.level.org.springframework=DEBUG
```

### Production
```properties
server.port=8080
spring.profiles.active=production
logging.level.org.springframework=INFO
```

## 📞 Support

Untuk pertanyaan atau masalah teknis, silakan hubungi Ajeng Azzahra Maharani.

---

**SIPATIK** - Pemberdayaan Umat Berkelanjutan 🕌
