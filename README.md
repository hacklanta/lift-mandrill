# lift-mandrill

This is a useful library for using Mandrill with Lift. It uses lift-json and Databinder's dispatch to make interacting with the Mandrill API pretty painless.

## Configuration

You'll need to add the following configuration parameters to your props file that Lift uses.

* **mandrill.apiKey** (required) - Your API key for Mandrill.
* **mandrill.endpointHost** (optional) - Some host other than mandrillapp.com, if needed.
* **mandrill.apiVersion** (optional) - Some version other than 1.0, if needed.

## Usage

To use the Mandrill API to send an email, you need to invoke `Mandrill.run` and pass in an instance of `MandrillApiCall`.

```scala
// At the top of your file
import com.hacklanta.mandrill._

// In your code that needs to invoke Mandrill
Mandrill.run(...)
```

The `Mandill.run` method will log in the event something goes wrong, but will never return anything. (Something that will be fixed in the future at some point.)

## Sending a regular email

To send an email using the Mandrill API, you create an instance of a `MandrillMessage`, create a `SendMandrillMessage` then pass that to `Mandrill.run`.

```scala
Mandrill.run(SendMandrillMessage(MandrillMessage(
  subject = "My subject",
  from_email = "hello@awesome.com",
  to = List(
    MandrillTo("bacon2@bacon.com", Some("recipient name if provided"), "to") //last param could be "cc" or "bcc"
  ),
  html = Some(...), // HTML version
  text = Some(...)  // plain text version
)))
```

## Sending a templated email

To send an email with a template on your Mandrill account, you need to know the template name, and the name of the `mc:edit` regions in the email. Once you have that, you can invoke `SendTemplateMandrillMessage`.

```scala
Mandrill.run(SendTemplateMandrillMessage(
  MandrillMessage(
    subject = "...",
    from_email = "...",
    to = List(...)
  ),
  template_name = "My awesome template.",
  template_content = List(
    Map("name" -> "edit region 1", "content" -> "..."),
    Map("name" -> "edit region 2", "content" -> "...")
  )
))
```
