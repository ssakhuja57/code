Imports System.IO

'Author: Shubham Sakhuja (ssakhuja@boston-engineering.com) 11/19/2013
' This tool finds all files nested in a given root directory and outputs the file name, extension, and location (tab-delimited) of each to a specified output file.
' To use this tool, specify the root directory as the first argument and the output file as the second argument.
' Example usage is: ExtractDirMeta.exe "C:\Users\me\root folder" "C:\test\folder\outputFile.txt"

Module Module1

    Sub main()

        Dim args As String() = ParseArgs(Command, 3)

        'For Each arg In args
        '    Console.WriteLine(arg)
        'Next

        Dim silentMode As Boolean
        Dim root As DirectoryInfo
        Dim outputFile As String

        Try
            silentMode = CBool(args(0))
            root = New DirectoryInfo(args(1))
            outputFile = args(2)
            For Each fil As FileInfo In getNestedFiles(root)
                'Console.WriteLine("0" & fil.Name & vbTab & fil.Extension & vbTab & fil.Directory.FullName)
                My.Computer.FileSystem.WriteAllText(outputFile, "000" & vbTab & fil.Name & vbTab & fil.Extension & vbTab & fil.Directory.FullName & vbCrLf, True)
            Next
        Catch e As System.ArgumentException
            Console.WriteLine()
            Console.WriteLine("This tool finds all files nested in a given root directory and outputs a dummy value (to work around any initial non-standard characters written to the file) followed by the file name, extension, and location (tab-delimited) of each file to a specified output file.")
            Console.WriteLine("To use this tool, specify whether to run in silent mode as the first argument, the root directory as the second argument, and the output file as the third argument.")
            Console.WriteLine("Example usage is: ExtractDirMeta.exe False ""C:\Users\me\root folder"" ""C:\test\folder\outputFile.txt""")
            Console.WriteLine("Press any key to exit")
            If Not silentMode Then
                Console.Read()
            End If
            Exit Sub
        End Try

    End Sub

    Function ParseArgs(command As String, maxArgs As Integer) As String()
        Dim rawArgs(), encapArgs() As String
        rawArgs = Split(command, " ")
        encapArgs = New String(maxArgs) {}


        Dim pos As Integer = 0
        Dim inArg As Boolean = False
        Dim freshStart As Boolean = True
        Dim temp As String = ""

        For Each arg As String In rawArgs
            If freshStart Then
                temp = arg
            End If
            If Left(arg, 1) = """" Then
                inArg = True
            End If
            If Right(arg, 1) = """" Then
                inArg = False
            End If
            If inArg Then
                If Not freshStart Then
                    temp = temp + " " + arg
                Else
                    freshStart = False
                End If
            Else
                If Not freshStart Then
                    temp = temp + " " + arg
                End If
                encapArgs(pos) = temp.Replace("""", "")
                temp = ""
                freshStart = True
                pos = pos + 1
                If pos = maxArgs Then
                    Exit For
                End If
            End If
        Next

        'For Each arg As String In rawArgs
        '    Console.WriteLine(arg)
        'Next

        'Console.WriteLine(".................")

        'For Each arg As String In encapArgs
        '    Console.WriteLine(arg)
        'Next

        Return encapArgs

    End Function

    Function getNestedFiles(rootDir As DirectoryInfo) As Generic.List(Of FileInfo)

        Dim NestedFiles As New Generic.List(Of FileInfo)

        'start: compensation for getNestedDirs not including root dir itself
        For Each fil In rootDir.EnumerateFiles
            NestedFiles.Add(fil)
        Next fil
        'end

        For Each dir As DirectoryInfo In getNestedDirs(rootDir)
            NestedFiles.AddRange(dir.EnumerateFiles)
        Next dir

        Return NestedFiles

    End Function

    'given a root directory, rootDir, returns a list of directories nested in it (but not rootDir itself)
    Function getNestedDirs(rootDir As DirectoryInfo) As Generic.List(Of DirectoryInfo)

        Dim SubDirs, Children As New Generic.List(Of DirectoryInfo)
        Children.AddRange(rootDir.EnumerateDirectories)
        If Children.Count = 0 Then
            Return Children
        Else
            SubDirs.AddRange(Children)
            For Each SubDir As DirectoryInfo In Children
                'Console.WriteLine(SubDir.FullName)
                SubDirs.AddRange(getNestedDirs(SubDir))
            Next SubDir
        End If

        Return SubDirs

    End Function


    'the below can be achieved by using fil.Name, where fil is of type FileInfo
    Function getFilename(path As String) As String
        Dim dot As Integer
        For i = path.Length To 1 Step -1
            If Mid(path, i, 1) = "\" Then
                dot = i
                Exit For
            End If
        Next
        getFilename = Mid(path, dot + 1)
    End Function

    'the below can be achieved by fil.Extension, where fil is of type FileInfo
    Function getExt(file As String) As String
        Dim dot As Integer
        For i = file.Length To 1 Step -1
            If Mid(file, i, 1) = "." Then
                dot = i
                Exit For
            End If
        Next
        getExt = Mid(file, dot + 1)
    End Function


End Module
