## AWS S3 & SpringBoot

Upload files to Amazon S3 via AWS SDK for Java 2.x using SpringBoot

## Getting Started

Follow the below instructions to get started with the source code:
- [Make sure you have all Requirements](#requirements)
- [Make sure to add AWS SDK for Java 2.x ](#Dependencies)
- [Configure storage properties](#configure-storage-properties)

## Requirements

- [AWS Account](https://aws.amazon.com/console/)
- [IAM user with access-key and secret-access-key (user’s credentials)](https://lightsail.aws.amazon.com/ls/docs/en_us/articles/amazon-lightsail-managing-access-for-an-iam-user)
- [Amazon S3 Bucket Example](https://docs.aws.amazon.com/code-library/latest/ug/java_2_s3_code_examples.html)

- Summary of Process done in AWS
  
  ![image](https://github.com/user-attachments/assets/120341ea-c49a-4214-a47a-9d5d066c6653)

## Dependencies

- AWS Java SDK Amazon S3:

```xml
<dependencies>
  <dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>s3</artifactId>
  </dependency>
</dependencies>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>software.amazon.awssdk</groupId>
      <artifactId>bom</artifactId>
      <version>2.17.124</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

## Configure storage properties

```
amazon.s3.bucket-name = ${AMAZON_S3_BUCKET_NAME}
amazon.s3.region = ${AWS_DEFAULT_REGION}
amazon.aws.credentials.access-key = ${AWS_ACCESS_KEY_ID}
amazon.aws.credentials.secret-access-key = ${AWS_SECRET_ACCESS_KEY}
```

In case of yaml

```
spring:
  profiles:
    active: ${CONFIG_PROFILE:uat}
```



