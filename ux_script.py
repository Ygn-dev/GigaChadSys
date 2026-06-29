import os
import re

files_phase_1 = [
    r"c:\Users\kenny\.gemini\antigravity-ide\scratch\GigaChadSys\C#\GigaChadSys\GigaChadSysWeb\Components\Pages\AdminEditarAdministrador.razor",
    r"c:\Users\kenny\.gemini\antigravity-ide\scratch\GigaChadSys\C#\GigaChadSys\GigaChadSysWeb\Components\Pages\AdminEditarEntrenador.razor",
    r"c:\Users\kenny\.gemini\antigravity-ide\scratch\GigaChadSys\C#\GigaChadSys\GigaChadSysWeb\Components\Pages\AdminSocioEditar.razor",
    r"c:\Users\kenny\.gemini\antigravity-ide\scratch\GigaChadSys\C#\GigaChadSys\GigaChadSysWeb\Components\Pages\AdminPerfil.razor",
    r"c:\Users\kenny\.gemini\antigravity-ide\scratch\GigaChadSys\C#\GigaChadSys\GigaChadSysWeb\Components\Pages\EntrenadorPerfil.razor",
    r"c:\Users\kenny\.gemini\antigravity-ide\scratch\GigaChadSys\C#\GigaChadSys\GigaChadSysWeb\Components\Pages\SocioPerfil.razor"
]

def refactor_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    # 1. Inject ToastService
    if "@inject ToastService" not in content:
        content = content.replace("@inject NavigationManager Nav", "@inject NavigationManager Nav\n@inject ToastService ToastService")

    # 2. Remove MensajeFormulario HTML block
    msg_block = re.compile(r'\s*@if \(!string\.IsNullOrWhiteSpace\(MensajeFormulario\)\)\s*\{\s*<div class="@ClaseMensajeFormulario">\s*@MensajeFormulario\s*</div>\s*\}')
    content = msg_block.sub('', content)

    # 3. Add IntentoGuardar and Guardando
    if "private bool IntentoGuardar" not in content:
        content = content.replace("@code {", "@code {\n    private bool Guardando { get; set; } = false;\n    private bool IntentoGuardar { get; set; } = false;\n")

    # 4. Remove variables
    content = re.sub(r'\s*private string MensajeFormulario \{ get; set; \} = "";\s*', '\n', content)
    content = re.sub(r'\s*private string ClaseMensajeFormulario \{ get; set; \} = "";\s*', '\n', content)

    # 5. Remove functions
    content = re.sub(r'\s*private void MostrarError\(string mensaje\)\s*\{\s*MensajeFormulario = mensaje;\s*ClaseMensajeFormulario = "[^"]*";\s*\}', '', content)
    content = re.sub(r'\s*private void MostrarExito\(string mensaje\)\s*\{\s*MensajeFormulario = mensaje;\s*ClaseMensajeFormulario = "[^"]*";\s*\}', '', content)

    # 6. Replace MostrarError and MostrarExito usages
    content = content.replace('MostrarError(', 'ToastService.ShowError(')
    content = content.replace('MostrarExito(', 'ToastService.ShowSuccess(')

    # 7. Button disabled and text
    # This is tricky without knowing exact button text. We will do manual replacement for the button and inputs.

    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(content)

for p in files_phase_1:
    if os.path.exists(p):
        refactor_file(p)
        print(f"Refactored {p}")
    else:
        print(f"Not found: {p}")
