namespace GigaChadSysWeb;

/// <summary>
/// Servicio singleton que controla la visibilidad global del componente AppAdmins.
/// Se puede activar/desactivar remotamente via endpoint REST.
/// </summary>
public class AppAdminsToggleService
{
    private bool _visible = false;

    public bool Visible => _visible;

    public event Action? OnChange;

    public void Activar()
    {
        _visible = true;
        OnChange?.Invoke();
    }

    public void Desactivar()
    {
        _visible = false;
        OnChange?.Invoke();
    }

    public void Toggle()
    {
        _visible = !_visible;
        OnChange?.Invoke();
    }
}
