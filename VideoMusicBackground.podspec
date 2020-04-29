
  Pod::Spec.new do |s|
    s.name = 'VideoMusicBackground'
    s.version = '0.0.1'
    s.summary = 'simple background music'
    s.license = ''
    s.homepage = 'https://github.com/francisdurante/music-background.git'
    s.author = 'Francis Durante'
    s.source = { :git => 'https://github.com/francisdurante/music-background.git', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
  end