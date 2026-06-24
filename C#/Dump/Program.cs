using System;
using System.Reflection;

class Program {
    static void Main() {
        try {
            var asm = Assembly.LoadFrom(@"C:\Users\kenny\Desktop\FCI\progra3\labs\TA\GigaChadSys\GigaChadSys\C#\GigaChadSys\GigaChadSysWeb\libs\GigaChad.Componentes.dll");
            foreach(var t in asm.GetTypes()) {
                Console.WriteLine(t.FullName);
            }
        } catch (ReflectionTypeLoadException ex) {
            foreach(var t in ex.Types) {
                if (t != null) Console.WriteLine(t.FullName);
            }
        }
    }
}
