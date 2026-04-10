# GoldenBungeeUtils ⚡

GoldenBungeeUtils is the administrative core in plugin format for your **BungeeCord** proxy. It is designed to centralize powerful moderation tools, unify chat systems, and provide general management features across your entire network of connected Minecraft servers.

## 🚀 Key Features

*   **🛡️ Advanced Administrative Chat System:** Independent channels for Staff (`/sc`) and Admins (`/ac`) for secure communication or daily use.
*   **🚧 Global Maintenance Mode:** Activate a real-time barrier across your entire proxy, setting instant whitelists using easily configurable commands.
*   **📣 Multi-Line Dynamic Announcements:** Configure asynchronous tasks to broadcast pre-determined messages across all your minigames at specified intervals.
*   **🔍 Professional Moderation Tools:** Systems like HelpOp (instant live reports to your staff), cross-network teleportation (`/goto`), finding active players (`/find`), and performance metrics to track employee time (`/stafftime`).
*   **🌐 100% Configurable Environment:** Easy-to-manage files (`config.yml` and `messages.yml`) to design the plugin's aesthetics using color codes, placeholders, and seamlessly fitting your community context (Native support for LuckPerms prefixes).

---

## ⚙️ Quick Installation

Being native to Classic BungeeCord and WaterFall servers, the installation is straightforward:

1. Download the compiled `.jar` file of **GoldenBungeeUtils**.
2. Navigate to the root of your local Proxy or remote Host and place it into your `/plugins/` folder.
3. *Critical Suggestion:* Install the core add-on/plugin [LuckPerms Bungee](https://luckperms.net/) inside your proxy, as GoldenBungeeUtils relies closely on its API to gracefully handle prefixes and permissions.
4. Restart your proxy node.
5. Access your `/plugins/GoldenBungeeUtils/` folder, personalize your `messages.yml` to your liking, and you're all set! Network secured!

## 💻 Featured Commands and Permissions

| Recommended Command | Base Permission | Main Description |
| :--- | :--- | :--- |
| `/sc [message]` | `gbu.staff.chat` | Toggles the restrictive staff chat. |
| `/ac [message]` | `gbu.admin.chat` | Toggles the restrictive chat for owners/administrators. |
| `/helpop <msg>` | `gbu.user.helpop` | User tool to alert online Staff live. |
| `/alert <msg>` | `gbu.staff.alert` | Sends an on-screen warning or broadcast across all servers at once. |
| `/goto <player>` | `gbu.staff.goto` | Magically travel to the backend/Spigot server where the target is located. |
| `/find <player>` | `gbu.staff.find` | Stealth tool that reveals the player's current activity/location. |
| `/stafftime` | `gbu.admin.time` | Review metrics and total presence time of your moderation team. |
| `/gbu maintenance`| `gbu.admin.cmd` | Toggles or manages maintenance mode network-wide. |
| `/gbu reload` | `gbu.admin.cmd` | Reload your changes in .yml files without memory leaks or dropping players. |

---

*GoldenBungeeUtils - Classic stability and network efficiency for BungeeCord.*
