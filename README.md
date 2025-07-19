# FocusQuest - Level Up Your Focus! 🚀

A highly engaging, gamified Android app that transforms digital wellness into an epic adventure. Built with Kotlin, this app helps users manage screen time through exciting quests, power-ups, and vehicle rewards.

## 🎮 **Engaging Features**

### 🏆 **Gamification System**
- **Level Up System**: Gain XP and level up from Focus Rookie to Mythic Legend
- **Daily Challenges**: Complete exciting focus quests for bonus rewards
- **Achievement System**: Unlock epic achievements and milestones
- **Streak Tracking**: Build and maintain focus streaks with fire emojis
- **Power-ups**: Use special abilities like Shield, Boost, Freeze, and Double Rewards

### 🎯 **Focus Quest Mode**
- **Interactive App Selection**: Choose apps to control with beautiful cards
- **Multiple Timer Options**: Quick Focus (5m), Power Focus (10m), Deep Focus (15m)
- **Real-time Progress**: Visual countdown with XP rewards preview
- **Focus Levels**: Apps gain focus levels as you avoid them consistently
- **Combo Multipliers**: Chain successful sessions for bonus XP

### 🏎️ **Epic Vehicle Collection**
- **Progressive Rewards**: Start with bikes, earn supercars, unlock rockets
- **Rarity System**: Common to Mythic vehicles with special effects
- **Vehicle Categories**: Bikes, Cars, Trucks, Boats, Planes, Rockets, Special
- **Unlock Animations**: Celebrate new vehicles with epic reveal animations
- **Garage Display**: Show off your collection in a beautiful 3D garage

### ⚡ **Power-up System**
- **🛡️ Distraction Shield**: Block all notifications during focus
- **⚡ Focus Boost**: Double XP for next session
- **❄️ Time Freeze**: Extend active timers
- **💰 Double Rewards**: 2x vehicle unlock progress

### 📊 **Advanced Analytics**
- **Focus Insights**: Track daily, weekly, monthly patterns
- **App Usage Heatmaps**: Visual representation of your digital habits
- **Progress Charts**: See your improvement over time
- **Leaderboards**: Compete with friends and global users
- **Achievement Gallery**: Display unlocked milestones

## 🎨 **Stunning UI/UX**

### 🌈 **Visual Design**
- **Material Design 3**: Latest Google design principles
- **Gradient Backgrounds**: Beautiful color transitions
- **Animated Elements**: Smooth transitions and micro-interactions
- **Custom Icons**: Hand-crafted emoji-style icons
- **Dark/Light Themes**: Automatic theme switching
- **Accessibility First**: High contrast, large touch targets

### 📱 **Interactive Elements**
- **Floating Action Buttons**: Quick access to power-ups
- **Progress Animations**: Satisfying level-up and XP animations
- **Card-based Layout**: Intuitive app selection with beautiful cards
- **Bottom Sheet Dialogs**: Smooth popup interactions
- **Haptic Feedback**: Physical responses to user actions

## Technical Architecture

### Built With
- **Kotlin** - Primary programming language
- **Room Database** - Local data persistence
- **Material Design 3** - UI components and theming
- **Navigation Component** - Fragment navigation
- **ViewBinding** - Type-safe view references
- **WorkManager** - Background task scheduling
- **Accessibility Service** - App monitoring and blocking

### Architecture Pattern
- **MVVM** (Model-View-ViewModel) with Repository pattern
- **Coroutines** for asynchronous operations
- **LiveData** for reactive UI updates
- **Dependency Injection** ready structure

### Database Schema
```
TimedApp -> App timer configurations
Reward -> Virtual vehicle rewards
UsageSession -> App usage tracking
IdlePeriod -> Idle time monitoring
```

## Permissions Required

- `QUERY_ALL_PACKAGES` - Access installed app list
- `PACKAGE_USAGE_STATS` - Monitor app usage
- `SYSTEM_ALERT_WINDOW` - Display blocking overlays
- `ACCESSIBILITY_SERVICE` - App monitoring and blocking
- `POST_NOTIFICATIONS` - Timer and reward notifications
- `FOREGROUND_SERVICE` - Background monitoring

## Installation & Setup

### Prerequisites
- Android Studio Arctic Fox (2020.3.1) or newer
- Android SDK API 24+ (Android 7.0+)
- Kotlin 1.9.10 or newer

### Build Instructions
1. Clone the repository
2. Open project in Android Studio
3. Sync Gradle dependencies
4. Build and run on device/emulator

### Initial Setup
1. Grant all required permissions when prompted
2. Enable Accessibility Service in device settings
3. Allow overlay permission for blocking screens
4. Select apps to monitor and set timer durations

## Project Structure

```
app/
├── src/main/java/com/rewardtimer/app/
│   ├── data/
│   │   ├── dao/           # Room DAO interfaces
│   │   ├── database/      # Database configuration
│   │   ├── model/         # Data models and entities
│   │   └── repository/    # Data repository
│   ├── service/           # Background services
│   ├── ui/
│   │   ├── adapters/      # RecyclerView adapters
│   │   ├── fragments/     # UI fragments
│   │   ├── viewmodels/    # MVVM ViewModels
│   │   └── MainActivity.kt
│   └── RewardTimerApplication.kt
└── src/main/res/
    ├── layout/            # XML layout files
    ├── navigation/        # Navigation graph
    ├── menu/             # Menu resources
    ├── values/           # Strings, colors, themes
    └── drawable/         # Vector drawables and icons
```

## Key Components

### Core Functionality
- **TimersFragment**: Main interface for app selection and timer management
- **InstalledAppsAdapter**: RecyclerView adapter for app list with timer controls
- **RewardTimerRepository**: Central data access layer
- **AppMonitoringService**: Background service for usage tracking

### Database Layer
- **RewardTimerDatabase**: Room database configuration
- **DAOs**: Data access objects for all entities
- **Entities**: Database models for apps, rewards, sessions, and idle periods

## Development Roadmap

### Phase 1: Core Timer Functionality ✅
- [x] App discovery and selection
- [x] Timer duration configuration
- [x] Basic UI with Material Design
- [x] Database schema and Room setup

### Phase 2: App Blocking System (In Progress)
- [ ] Accessibility Service implementation
- [ ] App blocking logic and overlay
- [ ] Timer countdown and expiration handling
- [ ] Notification system

### Phase 3: Reward System
- [ ] Idle period tracking
- [ ] Virtual vehicle rewards
- [ ] Reward gallery UI
- [ ] Achievement notifications

### Phase 4: Analytics & Polish
- [ ] Usage statistics and charts
- [ ] Settings and preferences
- [ ] Performance optimization
- [ ] Comprehensive testing

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Material Design team for design guidelines
- Android development community for best practices
- Open source contributors for inspiration and libraries
