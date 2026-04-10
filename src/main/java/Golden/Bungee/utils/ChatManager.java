package Golden.Bungee.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ChatManager {

    private final Set<UUID> staffChatToggled = new HashSet<>();
    private final Set<UUID> adminChatToggled = new HashSet<>();

    public boolean isStaffChatToggled(UUID uuid) {
        return staffChatToggled.contains(uuid);
    }

    public void toggleStaffChat(UUID uuid) {
        if (staffChatToggled.contains(uuid)) {
            staffChatToggled.remove(uuid);
        } else {
            staffChatToggled.add(uuid);
            adminChatToggled.remove(uuid);
        }
    }

    public void setStaffChat(UUID uuid, boolean enabled) {
        if (enabled) {
            staffChatToggled.add(uuid);
            adminChatToggled.remove(uuid);
        } else {
            staffChatToggled.remove(uuid);
        }
    }

    public boolean isAdminChatToggled(UUID uuid) {
        return adminChatToggled.contains(uuid);
    }

    public void toggleAdminChat(UUID uuid) {
        if (adminChatToggled.contains(uuid)) {
            adminChatToggled.remove(uuid);
        } else {
            adminChatToggled.add(uuid);
            staffChatToggled.remove(uuid);
        }
    }

    public void setAdminChat(UUID uuid, boolean enabled) {
        if (enabled) {
            adminChatToggled.add(uuid);
            staffChatToggled.remove(uuid);
        } else {
            adminChatToggled.remove(uuid);
        }
    }
}
