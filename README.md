Architecture 

The application follows a layered architecture built with Android Jetpack components. The data layer utilizes Room persistence library for local storage, structured with Entity classes (defining the accelerometer data model), DAO interfaces (handling database operations), and a Room Database instance. The domain layer manages sensor data capture via Android's Sensor Manager and Sensor Event Listeners, while the presentation layer employs View Model and Live Data to maintain UI-state consistency across configuration changes. The UI components are implemented with Fragments and Data Binding for efficient view updates. For file export, the Storage Access Framework (SAF) integrates with the system's file picker, ensuring secure external storage access compliant with modern Android permissions.

Functionality

Core functionality includes real-time accelerometer data capture (x, y, z axes) displayed in a live-updating UI. Sensor readings are timestamped and persistently stored locally via Room database at configurable sampling intervals. Users trigger CSV export through a dedicated button, which generates formatted files containing all logged sensor data (timestamps and axis values). The export process leverages Android's Content Resolver to write files to user-selected locations, supporting seamless sharing across applications. Additional features include sensor availability checks, storage permission handling, and responsive UI feedback during data operations.

Testing & Validation

Comprehensive testing involved instrumentation tests for database operations using Android X Test libraries, verifying CRUD functionality and data integrity under high-frequency sensor updates. Sensor simulation was performed via Android Emulator's virtual sensors and physical device testing across multiple manufacturers. Export functionality was validated through file system inspections and CSV parsing checks. Edge cases tested included storage permission revocations, sensor unavailability scenarios, and long-duration data collection (stress testing Room's insertion efficiency). UI tests confirmed proper Live Data updates and fragment navigation.

Development Reflection

Implementing Room from scratch taught me critical lessons in Android data architecture, particularly balancing real-time sensor sampling (every 50ms) with efficient database writes through background threading. The Storage Access Framework integration proved challenging due to Android's scoped storage restrictions, requiring deep understanding of Content Resolver and URI permissions. I learned to optimize Room transactions using bulk inserts and resolved UI freezes by offloading CSV generation to Async Task. This project significantly advanced my skills in lifecycle-aware components, modern Android storage paradigms, and performance optimization for sensor-driven applications.
