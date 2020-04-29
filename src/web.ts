import { WebPlugin } from '@capacitor/core';
import { VideoBackgroundMusicPlugin } from './definitions';

export class VideoBackgroundMusicWeb extends WebPlugin implements VideoBackgroundMusicPlugin {
  constructor() {
    super({
      name: 'VideoBackgroundMusic',
      platforms: ['web','android']
    });
  }

  async musicBackground(options: { value: string }): Promise<{value: string}> {
    //console.log('ECHO', options);
    return options;
  }
}

const VideoBackgroundMusic = new VideoBackgroundMusicWeb();

export { VideoBackgroundMusic };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(VideoBackgroundMusic);
