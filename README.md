아스키독을 이용한 책쓰기 템플릿(by AsciidoctorJ)
====================

* 작성자: 허니몬 <ihoneymon@gmail.com>

> 아스키독을 기반으로 저술활동을 하자.

## 요구사항
* 그레이들(Gradle)을 실행하기 위해서 JDK 설치
  * [SDKMan](https://sdkman.io/)
  * https://adoptopenjdk.net/


## 문서빌드
```
$ git clone git@github.com:ihoneymon/writer-book-template-by-asciidoctorj.git
$ cd writer-book-template-by-asciidoctorj
$ ./gradlew run
```

`publication` 디렉터리가 생성되고, 그 하위에 다음 파일이 생성됩니다. 

* HTML 파일: `book.html`
* PDF 파일 `book.pdf`
* Docbook 파일: `book.xml`
* EPUB3 파일: `book.epub`

## 참고
* [Asciidoc 으로 전자책 쓰기 - SpringCamp 2016 LETS](https://gist.github.com/namjae/8f1bbd6b6d4765aec86febf486cfba22)
