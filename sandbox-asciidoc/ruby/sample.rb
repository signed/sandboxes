require 'asciidoctor' # <1>

puts Asciidoctor.convert_file 'mysample.adoc', to_file: false # <2>