
<p>
      This program provides a command line interface for blocking and unblocking URLs, printing the list of blocked sites and downloading URL contents while applying different denying options. The program uses the following design patterns:
    </p>
    <ul>
      <li>Command Design Pattern for implementing the console based commands.</li>
      <li>Factory Design Pattern for implementing the denying options.</li>
    </ul>
    <h2>Blocked URLs</h2>
    <p>
      The program maintains a list of blocked URLs in a file named "blocked.txt". If the file does not exist, it is created. The file is updated every time a URL is blocked or unblocked. The program handles file I/O errors by outputting an error message to the console.
    </p>
    <h2>Block Command</h2>
    <p>
      The "b" command blocks a URL and adds it to the list of blocked URLs in the "blocked.txt" file. Before adding the URL, the program checks the syntax of the URL to ensure that it is compliant with RC 2396. If the URL syntax is invalid, the program outputs an error message to the console. The program does not check the connectivity of the URL. The program handles file I/O errors by outputting an error message to the console.
    </p>
    <h2>Unblock Command</h2>
    <p>
      The "u" command unblocks a URL and updates the "blocked.txt" file. If the URL is not in the list of blocked URLs, the program remains silent. The program handles file I/O errors by outputting an error message to the console.
    </p>
    <h2>Print Command</h2>
    <p>
      The "p" command prints the list of blocked URLs in alphabetical order. The program handles file I/O errors by outputting an error message to the console.
    </p>
    <h2>Download Command</h2>
    <p>
      The "d" command downloads the contents of a URL to a file with a given name. The command takes an optional denying option, which determines which content to deny. The denying options are implemented using the Factory Design Pattern. The following denying options are available:
    </p>
    <ul>
      <li>"i": block images (HTTP header Content-Type starts with "image/").</li>
      <li>"c": block HTTP responses containing cookies (in HTTP headers response).</li>
      <li>"h": block HTML documents (HTTP header Content-Type == "text/html").</li>
      <li>"b": deny access to blocked sites. If the URL is in the list of blocked URLs, the program outputs an error message to the console. The program handles file I/O errors by outputting an error message to the console.</li>
    </ul>
    <p>
      The program handles file I/O errors by outputting an error message to the console.
    </p>
