# About

This is a Java library that provides collection implementations based on SQLite.

Using it is useful in the following scenarios:

A) You need to store something in a huge collection that does not fit into
memory. By using the SQLite-based collections, you can move the storage to disk
without having to refactor your collection-API-based code.

B) You have some data stored in an existing SQLite database and you want to
access some part of it using the collections API. Just wrap the existing
database into one of the collections and don't worry about changing your
existing code.

# License

This library is released under the terms of the GNU Lesser General Public
License.

See [LGPL.md](LGPL.md) and [GPL.md](GPL.md) for details.

# Download

We provide access to the artifacts via our
[own Maven repository](https://mvn.topobyte.de) at these coordinates:

    de.topobyte:sqlite-collections:0.0.2

You can also browse the repository online:

<https://mvn.topobyte.de/de/topobyte/sqlite-collections/>
