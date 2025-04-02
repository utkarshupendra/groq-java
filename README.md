# **Groq Java Client**

`Groq Java Client` is a lightweight Java library that provides a simplified wrapper over the Groq REST API. Users only need to interact with the `LlmApiService` class, making it easy to send requests and retrieve responses without directly dealing with the Groq API.

This library abstracts the complexity of interacting with Groq's REST API and provides intuitive methods to handle requests and responses with proper error handling mechanisms.

---

## **Features**

- Simple and intuitive API for interacting with Groq's LLM service.
- Built-in support for HTTP headers, request body serialization, and API key management.
- Handles empty or erroneous responses from the API, throwing descriptive exceptions.
- Default configuration for Groq's base API URL with optional overrides.

---

## **Installation**

To use this library in your project, include the following dependencies in your `pom.xml` if you're using Maven:

```xml
<dependency>
    <groupId>com.utkarshupendra</groupId>
    <artifactId>groq-java</artifactId>
    <version>{version}</version>
</dependency>
```

If you are using Gradle, add the following to your `build.gradle` file:

```gradle
implementation 'com.utkarshupendra:groq-java:{version}'
```

---

## **Usage**

### **1. Configuration**

Before using the library, ensure you have set the required property `llm.apiKey` in your application properties or environment variables. This key is required to authenticate all interactions with the Groq API.

Set the following properties in your `application.properties` or `application.yml` file:

```properties
# Required: Your Groq API key
llm.apiKey=YOUR_API_KEY

# Optional: Base URL for Groq API (default is https://api.groq.com/openai/v1/)
llm.baseUrl=https://api.groq.com/openai/v1/

# Optional: Sub URL for the completion endpoint (default is chat/completions)
llm.subUrl.completion=chat/completions
```

> **Note**: Replace `YOUR_API_KEY` with the actual API key provided by Groq.

---

### **2. Getting Started**

The main class you need to interact with is the `LlmApiService`. It simplifies sending requests and receiving LLM responses.

> **Note:** In the recent spring boot releases, the application might not detect a bean of a class coming from library. Consider defining a `@Bean` of type `LlmApiService` in such cases.

#### Example: Sending a Request to Groq API
```java
import com.utkarshupendra.groq_client.api.LlmApiService;
import com.utkarshupendra.groq_client.dto.LlmRequest;
import com.utkarshupendra.groq_client.dto.LlmMessage;
import com.utkarshupendra.groq_client.dto.LlmResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SampleApp {
    @Autowired
    private LlmApiService llmApiService;

    public void getCompletionExample() {
        // Create request
        LlmRequest request = new LlmRequest(
            "gpt-4", // Model name
            List.of(new LlmMessage("user", "Hello, can you help me?")) // List of messages
        );

        // Call the API and handle the response
        LlmResponse response = llmApiService.getCompletion(request);

        // Print the response
        System.out.println("Response:");
        response.getChoices().forEach(choice ->
            System.out.println(choice.getMessage().getContent())
        );
    }
}
```

In this example:
- The user does **not** need to interact with Groq's REST API directly.
- The `LlmApiService` takes care of authentication, serialization, and error handling.
- The exact list of models are available on Groq [here](https://console.groq.com/docs/tool-use).

#### Request Example
The library sends a POST request to the Groq API endpoint configured in the properties. For example:
```http
POST https://api.groq.com/openai/v1/chat/completions
Authorization: Bearer YOUR_API_KEY
Content-Type: application/json

{
    "model": "gpt-4",
    "messages": [
        {
            "role": "user",
            "content": "Hello, can you help me?"
        }
    ]
}
```

---

## **Exception Handling**

The library includes custom exceptions to make error handling easier for users:

1. **`LlmServiceException`**:
    - Thrown when the Groq API returns an error response (e.g., HTTP 500).
    - Provides access to the original request and response for debugging.

2. **`LlmEmptyResponseException`**:
    - Thrown when the Groq API returns an empty or invalid response.
    - Helps detect cases where the service is down or the request format is incorrect.

Example:
```java
try {
    LlmResponse response = llmApiService.getCompletion(request);
} catch (LlmServiceException ex) {
    System.err.println("API returned an error: " + ex.getMessage());
} catch (LlmEmptyResponseException ex) {
    System.err.println("Empty response from the API");
}
```

## **Key Classes**

### 1. `LlmApiService`
The main service class for interacting with Groq's LLM API. This class abstracts request creation, API communication, and response parsing. It requires the `llm.apiKey` property to be set.

### 2. `LlmRequest`
Encapsulates the request payload for Groq API, including:
- The model to use (e.g., `gpt-4`)
- A list of messages.

### 3. `LlmResponse`
Represents the response received from the Groq API, including:
- Choices: The generated outputs from the LLM.
- Usage: Metadata about the request, like token counts and processing time.

---

## **Contributing**

If you’d like to contribute to this library:
1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-branch-name
   ```
3. Commit your changes and create a pull request.

---

## **License**

This project is licensed under the MIT License. See the `LICENSE` file for details.

---
