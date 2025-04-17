# Bos

Bos is a mobile-based Point of Sale (POS) system designed to streamline product scanning and
inventory management.

# Mobile App
The mobile application is built using the MVVM architecture and leverages modern Android components
for a seamless user experience.

* Barcode scanning is implemented using Google's ML Kit, allowing fast and reliable barcode
  detection.
* CameraX is integrated for real-time image analysis and barcode scanning.
* The app includes runtime camera permission handling to ensure secure access.
* Retrofit is used to handle all HTTP requests efficiently, connecting the app to the backend
  server.

 # Backend
  The backend server is developed in Go using the standard library to keep things lightweight and
  efficient.
* It exposes endpoints that return detailed information about barcodes scanned from the mobile app.
* JWT (JSON Web Tokens) are used for secure authentication and authorization of requests.
