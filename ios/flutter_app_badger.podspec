#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'flutter_app_badger'
  s.version          = '1.3.0'
  s.summary          = 'Plugin to update the app badge on the launcher (both for Android, iOS and macOS)'
  s.description      = <<-DESC
  Plugin to update the app badge on the launcher (both for Android, iOS and macOS)
                       DESC
  s.homepage         = 'https://github.com/g123k/flutter_app_badger'
  s.license          = { :file => '../LICENSE' }
  s.author           = 'Edouard Marquez'
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  
  s.ios.deployment_target = '8.0'
  s.resource_bundles = {'flutter_app_badger_privacy' => ['PrivacyInfo.xcprivacy']}
end

