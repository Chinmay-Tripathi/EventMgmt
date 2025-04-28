# Event Management Android Application

A comprehensive Android application for managing events, built with modern Android development practices and Material Design components.

## Features

### Event Management
- Create, view, edit, and delete events
- Add event details including title, description, date, time, and location
- Location selection using Android's built-in Geocoder
- Current location detection
- Event categorization and filtering

### Attendee Management
- Add and manage event attendees
- Track attendee status (confirmed, pending, declined)
- Send notifications to attendees
- View attendee details and contact information

### User Interface
- Material Design components for a modern look and feel
- Responsive layouts for different screen sizes
- Intuitive navigation and user experience
- Search and filter capabilities

## Technical Stack

- **Language**: Java
- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Components**: Material Design
- **Location Services**: Android Geocoder
- **Database**: SQLite with Room
- **Permissions**: Runtime permissions for location access

## Setup Instructions

1. **Prerequisites**
   - Android Studio (latest version recommended)
   - Java Development Kit (JDK) 8 or higher
   - Android SDK with API 21 or higher

2. **Installation**
   ```bash
   # Clone the repository
   git clone https://github.com/yourusername/EventMgmt.git
   
   # Open the project in Android Studio
   # Wait for Gradle sync to complete
   ```

3. **Build and Run**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio
   - Select your target device
   - Wait for the app to install and launch

## Usage Guide

### Creating an Event
1. Tap the "+" button on the main screen
2. Fill in event details:
   - Title
   - Description
   - Date and time
   - Location (use the location picker or current location)
3. Save the event

### Managing Attendees
1. Open an event
2. Navigate to the "Attendees" tab
3. Add new attendees or manage existing ones
4. Update attendee status as needed

### Location Selection
1. When creating/editing an event, tap the location field
2. Choose between:
   - Searching for a location
   - Using current location
3. Select from the search results
4. Confirm the location

## Permissions

The app requires the following permissions:
- `ACCESS_FINE_LOCATION`: For precise location services
- `ACCESS_COARSE_LOCATION`: For approximate location services
- `INTERNET`: For location services and data synchronization

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, please open an issue in the GitHub repository or contact the development team.

## Acknowledgments

- Material Design components
- Android Geocoder API
- Android Room Persistence Library