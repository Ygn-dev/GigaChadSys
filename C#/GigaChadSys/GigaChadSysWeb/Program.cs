using GigaChadSys.Servicios;
using GigaChadSysWeb;
using GigaChadSysWeb.Components;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();

// Add global HttpClient pointing to the Java REST Backend
builder.Services.AddScoped(_ => new HttpClient
{
    BaseAddress = new Uri(
        "http://localhost:8080/GigaChadSys-REST-1.0-SNAPSHOT/webresources/"
    )
});

// Registrar todos los servicios REST que consumen el backend Java
builder.Services.AddGigaChadSysServicios();

// Singleton para controlar la visibilidad del botón AppAdmins
builder.Services.AddSingleton<AppAdminsToggleService>();

// Generar Reportes
builder.Services.AddScoped<ReporteServicio>();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
    app.UseHsts();
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);
app.UseHttpsRedirection();

app.UseAntiforgery();

app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

// ── REST API: Control remoto del botón AppAdmins ──
app.MapGet("/api/appadmins/status", (AppAdminsToggleService svc) =>
    Results.Ok(new { visible = svc.Visible }));

app.MapPost("/api/appadmins/activar", (AppAdminsToggleService svc) =>
{
    svc.Activar();
    return Results.Ok(new { visible = true, mensaje = "Botón AppAdmins activado" });
});

app.MapPost("/api/appadmins/desactivar", (AppAdminsToggleService svc) =>
{
    svc.Desactivar();
    return Results.Ok(new { visible = false, mensaje = "Botón AppAdmins desactivado" });
});

app.MapPost("/api/appadmins/toggle", (AppAdminsToggleService svc) =>
{
    svc.Toggle();
    return Results.Ok(new { visible = svc.Visible, mensaje = $"Botón AppAdmins ahora está {(svc.Visible ? "activado" : "desactivado")}" });
});

app.MapPost("/api/appadmins/ejecutar", (AppAdminsToggleService svc) =>
{
    svc.Ejecutar();
    return Results.Ok(new { visible = true, mensaje = "Botón AppAdmins activado y ejecutado en TODOS los dispositivos" });
});

app.Run();

