declare module "@capacitor/core" {
    interface PluginRegistry {
        VideoBackgroundMusic: VideoBackgroundMusicPlugin;
    }
}
export interface VideoBackgroundMusicPlugin {
    musicBackground(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
