package com.anodiam.StudentSignup.secretManager;

import com.google.cloud.secretmanager.v1.*;
import com.google.protobuf.ByteString;

public class Quickstart {

    public void quickstart1() throws Exception{
        System.out.println("Quickstart Config Proj & Secret");
        String projectId = "anodiam-mvp";
        String secretId = "projects/914556095412/secrets/program-secret";
        System.out.println("Quickstart Config Proj " + projectId + " & Secret " + secretId);
        quickstart(projectId, secretId);
    }

    public void quickstart(String projectId, String secretId) throws Exception {
        System.out.println("In Quickstart");
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()){
            ProjectName projectName = ProjectName.of(projectId);
            Secret secret = Secret.newBuilder().setReplication(Replication.newBuilder()
                                .setAutomatic(Replication.Automatic.newBuilder().build()).build()).build();
            Secret createdSecret = client.createSecret(projectName, secretId, secret);
            SecretPayload payload = SecretPayload.newBuilder().setData(ByteString
                                    .copyFromUtf8("hello world!")).build();
            SecretVersion addedVersion = client.addSecretVersion(createdSecret.getName(), payload);
            AccessSecretVersionResponse response = client.accessSecretVersion(addedVersion.getName());
            String data = response.getPayload().getData().toStringUtf8();
            System.out.printf("Plaintext: %s\n", data);
        }
    }
}
