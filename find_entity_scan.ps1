Add-Type -AssemblyName System.IO.Compression.FileSystem

$jars = @(
    "$env:USERPROFILE\.m2\repository\org\springframework\boot\spring-boot-autoconfigure\4.1.0\spring-boot-autoconfigure-4.1.0.jar",
    "$env:USERPROFILE\.m2\repository\org\springframework\boot\spring-boot-data-jpa\4.1.0\spring-boot-data-jpa-4.1.0.jar",
    "$env:USERPROFILE\.m2\repository\org\springframework\boot\spring-boot-persistence\4.1.0\spring-boot-persistence-4.1.0.jar",
    "$env:USERPROFILE\.m2\repository\org\springframework\boot\spring-boot-data-commons\4.1.0\spring-boot-data-commons-4.1.0.jar"
)

foreach ($jarPath in $jars) {
    if (Test-Path $jarPath) {
        Write-Output "=== $jarPath ==="
        $zip = [System.IO.Compression.ZipFile]::OpenRead($jarPath)
        $zip.Entries | Where-Object { $_.Name -like '*EntityScan*' } | ForEach-Object { Write-Output $_.FullName }
        $zip.Dispose()
    } else {
        Write-Output "NOT FOUND: $jarPath"
    }
}
