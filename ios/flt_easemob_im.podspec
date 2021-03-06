#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#
Pod::Spec.new do |s|
  s.name             = 'flt_easemob_im'
  s.version          = '0.0.1'
  s.summary          = 'A IM Flutter plugin based on easemob'
  s.description      = <<-DESC
A IM Flutter plugin based on easemob
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.dependency 'Hyphenate'
  s.ios.deployment_target = '8.0'
end

