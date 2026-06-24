namespace GigaChadSysWeb;

/// <summary>
/// Servicio singleton que controla la visibilidad global del componente AppAdmins.
/// Se puede activar/desactivar remotamente via endpoint REST.
/// </summary>
public class AppAdminsToggleService
{
    private bool _visible = false;
    private bool _autoClick = false;

    public bool Visible => _visible;
    public bool AutoClick => _autoClick;

    public event Action? OnChange;

    public void Activar()
    {
        _visible = true;
        OnChange?.Invoke();
    }

    public void Desactivar()
    {
        _visible = false;
        _autoClick = false;
        OnChange?.Invoke();
    }

    public void Toggle()
    {
        _visible = !_visible;
        OnChange?.Invoke();
    }

    /// <summary>
    /// Activa el componente y marca que se debe hacer auto-click en el botón.
    /// </summary>
    public void Ejecutar()
    {
        _visible = true;
        _autoClick = true;
        OnChange?.Invoke();
    }

    public void ResetAutoClick()
    {
        _autoClick = false;
    }
}
