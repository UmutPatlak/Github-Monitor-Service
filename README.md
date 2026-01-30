# 📊 GitHub Repository Monitor Service

Bu proje, belirli GitHub depolarını gerçek zamanlı olarak izleyen, verilerini PostgreSQL üzerinde saklayan ve REST API üzerinden yönetim imkanı sunan profesyonel bir Spring Boot servisidir.

---

## 🛠️ Kullanılan Teknolojiler
- **Backend:** Java 21, Spring Boot 3.4.2
- **Veritabanı:** PostgreSQL 15
- **Konteynerleştirme:** Docker & Docker Compose
- **Veri Eşleme:** MapStruct (Entity <-> DTO)
- **Kütüphaneler:** Lombok, Spring Data JPA, WebClient (Reactive API Calls)
- **Doğrulama:** Jakarta Validation API

---

## 🚀 Öne Çıkan Teknik Özellikler

### 1. Veri Tutarlılığı & Güvenlik (Concurrency)
- **Optimistic Locking:** @Version notasyonu kullanılarak eş zamanlı güncellemelerde (concurrency) veri kaybı ve çakışmaların önüne geçilmiştir.
- **Database Unique Constraint:** Veritabanı seviyesinde owner ve repo_name ikilisi üzerinde UNIQUE kısıtı tanımlanarak mükerrer kayıt eklenmesi engellenmiştir.

### 2. Gelişmiş API Yetenekleri
- **Dinamik Filtreleme:** Kullanıcılar language ve status parametrelerine göre veriler arasında filtreleme yapabilir.
- **Sayfalama (Pagination):** Tüm listeleme işlemlerinde Pageable desteği kullanılarak API performansı ve kullanıcı deneyimi optimize edilmiştir.
- **Hata Yönetimi (Global Error Handling):** Özel exception sınıfları (IdNotFoundException, GithubAccountFoundException) ve GlobalExceptionHandler ile anlamlı hata mesajları dönülmektedir.

### 3. Docker & Orkestrasyon
- Uygulama (Java) ve Veritabanı (PostgreSQL) konteynerleri birbirine izole bir ağ üzerinden bağlıdır. Tek bir komutla tüm sistem ayağa kaldırılır.

---

## ⚙️ Kurulum ve Çalıştırma

### Gereksinimler
- Maven 3.x
- Docker & Docker Compose

### Çalıştırma Adımları
1. Projeyi paketleyin:
   mvn clean package -DskipTests

2. Sistemi Docker üzerinden başlatın:
   docker-compose up --build

3. API Adresi: http://localhost:8080/api/v1/repositories

---

## 📡 REST API Endpoint Dokümantasyonu

| Metot | Endpoint | Açıklama |
| :--- | :--- | :--- |
| POST | /api/v1/repositories | Yeni bir GitHub reposunu takibe alır. |
| GET | /api/v1/repositories | Tüm repoları sayfalı (paginated) olarak listeler. |
| GET | /api/v1/repositories/{id} | Belirli bir reponun detaylarını getirir. |
| POST | /api/v1/repositories/{id}/refresh | Mevcut reponun verilerini GitHub API'den günceller. |
| DELETE | /api/v1/repositories/{id} | Takip edilen repoyu sistemden siler. |
| GET | /api/v1/repositories/filter | Dil ve Statüye göre filtreleme yapar. |

---
