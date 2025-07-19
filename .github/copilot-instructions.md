# Copilot Instructions for Reward Timer Android App

<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

## Project Overview
This is an Android app built with Kotlin that helps users manage their screen time by setting timers for specific apps and rewarding them with virtual vehicles for avoiding app usage.

## Architecture & Technologies
- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel) with Repository pattern
- **Database**: Room (SQLite)
- **UI**: Material Design 3, View Binding, Navigation Component
- **Background Processing**: Accessibility Service, WorkManager
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Key Features to Implement
1. **App Timer Management**: Select apps from installed apps list, set 5 or 10-minute timers
2. **App Blocking**: Use Accessibility Service to detect and block restricted apps after timer expires
3. **Reward System**: Track idle periods (30-60 minutes) and unlock virtual vehicles as rewards
4. **Visual Progress**: Show remaining timer progress with progress bars and countdown
5. **Notifications**: Timer expiration, reward unlock, and blocking notifications
6. **Material Design**: Clean UI with dark/light theme support and high accessibility

## Code Guidelines
- Use **ViewBinding** for all layouts
- Follow **Material Design 3** principles
- Implement proper **error handling** and **loading states**
- Use **coroutines** for async operations
- Follow **Android architecture best practices**
- Ensure **accessibility compliance**
- Write **clean, readable code** with proper documentation

## Database Schema
- `TimedApp`: Stores app timer configurations
- `Reward`: Virtual vehicle rewards earned by users
- `UsageSession`: Tracks app usage sessions
- `IdlePeriod`: Tracks periods when user avoids restricted apps

## Security & Permissions
- Usage Stats permission for app monitoring
- Accessibility Service for app blocking
- Overlay permission for blocking screens
- Notification permission for alerts
- Query all packages permission for app list

## Testing Strategy
- Unit tests for ViewModels and Repository
- Integration tests for database operations
- UI tests for critical user flows
