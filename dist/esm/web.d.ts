import { WebPlugin } from '@capacitor/core';
import { VideoBackgroundMusicPlugin } from './definitions';
export declare class VideoBackgroundMusicWeb extends WebPlugin implements VideoBackgroundMusicPlugin {
    constructor();
    musicBackground(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
declare const VideoBackgroundMusic: VideoBackgroundMusicWeb;
export { VideoBackgroundMusic };
