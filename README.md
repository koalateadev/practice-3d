# Practice 3D

Practice 3D is an Android application designed to help artists and students practice drawing by displaying basic 3D geometric shapes. Built with Sceneform, it allows users to view and manipulate still-form objects in 3D space, providing a reference tool for improving drawing skills and understanding form.

## Features

- **Multiple 3D Shapes**: View various geometric primitives including:
  - Cube
  - Sphere
  - Cylinder
  - Cone
  - Pyramid

- **Interactive Controls**:
  - Shape selection through an intuitive carousel interface
  - Model transformation controls (position, rotation, scale)
  - Transparency adjustment for better reference overlays
  - Lock/unlock functionality to freeze transformations

- **Camera Integration**:
  - Overlay 3D shapes on your camera view
  - Perfect for drawing from life with geometric references
  - Toggle between pure 3D view and camera overlay

- **Customization**:
  - Adjustable model transparency
  - Configurable lighting and materials
  - Touch-based manipulation of 3D objects

## Technologies

- **Language**: Kotlin
- **3D Rendering**: [Sceneform](https://github.com/thomasgorisse/sceneform) (v1.20.1)
- **Architecture**: MVVM with Android Architecture Components
- **Dependency Injection**: Hilt/Dagger
- **Camera**: CameraX
- **UI**: Material Design Components, ViewBinding

## Requirements

- Android device with API level 24 (Android 7.0) or higher
- Camera permission for overlay functionality
- Approximately 50MB of storage space

## Installation

### Building from Source

1. Clone the repository:
   ```bash
   git clone https://github.com/koalateadev/practice-3d.git
   cd practice-3d
   ```

2. Open the project in Android Studio

3. Sync Gradle dependencies

4. Build and run on your device or emulator:
   ```bash
   ./gradlew assembleDebug
   ```

## Usage

1. **Launch the app** - The app starts with a default cube displayed in 3D space

2. **Select a shape**:
   - Tap the shape selection control
   - Swipe through the carousel to choose your desired geometric form
   - The shape updates in real-time

3. **Transform the model**:
   - Pinch to scale
   - Drag to rotate and position
   - Use the lock button to freeze transformations

4. **Adjust transparency**:
   - Access the model transformation controls
   - Slide to adjust opacity for overlay reference drawing

5. **Enable camera overlay**:
   - Tap the camera control button
   - Grant camera permission when prompted
   - Position 3D shapes over real-world objects for comparison

## Project Structure

```
app/src/main/java/com/practice3d/
├── ui/
│   ├── MainActivity.kt           # Main activity and fragment management
│   ├── MainViewModel.kt          # Central view model for app state
│   ├── control/                  # UI control fragments
│   │   ├── ModelSelectionFragment.kt
│   │   ├── ModelTransformationFragment.kt
│   │   ├── CameraControllerFragment.kt
│   │   └── OptionsFragment.kt
│   └── display/                  # Display fragments
│       ├── ModelFragment.kt      # 3D model rendering
│       └── CameraFragment.kt     # Camera preview
├── infrastructure/
│   └── model/                    # 3D model utilities
│       ├── Shapes.kt             # Shape definitions
│       ├── CustomShapeFactory.kt # 3D shape generation
│       └── DragTransformableNode.kt
└── extensions/                   # Kotlin extensions
```

## Development

### Building

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumentation tests
./gradlew connectedAndroidTest
```

## Dependencies

Key libraries used in this project:

- **Sceneform** (1.20.1) - 3D rendering engine
- **CameraX** (1.1.0) - Modern camera API
- **Hilt** (2.38.1) - Dependency injection
- **Material Components** (1.4.0) - UI components
- **AndroidX Libraries** - Core Android Jetpack components

## Roadmap

### MVP
- ✅ Model transformations - size, position, rotation
- ⏳ Fix multiple model display
- ⏳ Change default background
- ⏳ Add permission denied helper
- ⏳ Add bottom sheet titles

### Beta
- ⏳ Add onboarding
- ⏳ Add playstore images
- ⏳ Add help button
- ⏳ Fix already selected controller
- ⏳ Change color
- ⏳ Change lighting
- ⏳ Fix chevron direction
- ⏳ Add tap to hide overlays
- ⏳ Add button selected change
- ⏳ Fix theming
- ⏳ Make bottom sheet not clickable

### Future
- ⏳ Make bottom sheet dismissable
- ⏳ Add image capture
- ⏳ Firebase analytics
- ⏳ Firebase crashlytics
- ⏳ Help screen

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is open source. Please check the repository for license information.

## Acknowledgments

- Built with [Sceneform by Thomas Gorisse](https://github.com/thomasgorisse/sceneform)
- Inspired by traditional art education techniques for learning form and perspective
