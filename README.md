# music-background

import { Plugins } from "@capacitor/core";
const { VideoBackgroundMusic } = Plugins;

....
else if(res.data.data == "request_fleektok")
{
    res = await VideoBackgroundMusic.musicBackground();
}

// res = data:{thumbnail:`thumbnail path`, data:[`video path`]}
  
.....
